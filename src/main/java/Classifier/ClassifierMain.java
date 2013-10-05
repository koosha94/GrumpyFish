package Classifier;


import java.io.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: koosha
 * Date: 3/21/13
 * Time: 7:19 PM
 * To change this template use File | Settings | File Templates.
 */

class Pair<L,R> {

    private final L left;
    private final R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() { return left; }
    public R getRight() { return right; }

    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        return this.left.equals(pairo.getLeft()) &&
                this.right.equals(pairo.getRight());
    }

}

public class ClassifierMain {

    public static void main(String args[]){
        //ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        //BrainVAO brain = context.getBean("brain", BrainVAO.class);
        List<BrainVAO> brains = new ArrayList<BrainVAO>();



        String surfaceDirectory = new String(args[0] + "\\NKI-RS-22-");
        String measuresDirectory = new String(args[1]+"\\NKI-RS-22-");
        String dumpFilesDirectory = args[2] ;



        for (Integer ptr = 3; ptr<args.length ; ptr++){
            try {
                Integer c = Integer.parseInt(args[ptr]);
                BrainVAO brain = new BrainVAO();
                BrainBuilder brainBuilder = new BrainBuilder();
                brainBuilder.setBrainVAO(brain);
                FileInputStream inputStream = new FileInputStream(surfaceDirectory+c+"\\affine_sulci.vtk");
                brainBuilder.setInputStream(inputStream);
                brainBuilder.build();
                System.out.println("made the base brain");
                String gradstr = new String("");
                for(int grad = 0 ; grad <1 ; grad++) {
                    inputStream = new FileInputStream(measuresDirectory+c+"\\"+gradstr+"affine_geodesic_depth_rescaled.vtk");
                    brainBuilder.addNewFeatureFile(inputStream,gradstr+"affine_geodesic_depth_rescaled");
                    inputStream = new FileInputStream(measuresDirectory+c+"\\"+gradstr+"affine_lh.pial.area.vtk");
                    brainBuilder.addNewFeatureFile(inputStream,gradstr+"affine_lh.pial.area");
                    inputStream = new FileInputStream(measuresDirectory+c+"\\"+gradstr+"affine_lh.pial.geodesic_depth.vtk");
                    brainBuilder.addNewFeatureFile(inputStream,gradstr+"affine_lh.pial.geodesic_depth");
                    inputStream = new FileInputStream(measuresDirectory+c+"\\"+gradstr+"affine_lh.pial.mean_curvature.vtk");
                    brainBuilder.addNewFeatureFile(inputStream,gradstr+"affine_lh.pial.mean_curvature");
                    inputStream = new FileInputStream(measuresDirectory+c+"\\"+gradstr+"affine_lh.pial.travel_depth.vtk");
                    brainBuilder.addNewFeatureFile(inputStream,gradstr+"affine_lh.pial.travel_depth");
                    inputStream = new FileInputStream(measuresDirectory+c+"\\"+gradstr+"affine_lh.sulc.vtk");
                    brainBuilder.addNewFeatureFile(inputStream,gradstr+"affine_lh.sulc");
                    inputStream = new FileInputStream(measuresDirectory+c+"\\"+gradstr+"affine_lh.thickness.vtk");
                    brainBuilder.addNewFeatureFile(inputStream,gradstr+"affine_lh.thickness");
                    inputStream = new FileInputStream(measuresDirectory+c+"\\"+gradstr+"affine_travel_depth_rescaled.vtk");
                    brainBuilder.addNewFeatureFile(inputStream,gradstr+"affine_travel_depth_rescaled");
                    gradstr = gradstr + "gradient_";
                }
               /* brainBuiluder.computeFeatures();
                BrainVAO brainVAO = brainBuilder.getBrainVAO();
                Map<Integer, LabelVAO> labelVAOs = brainVAO.getLabelVAOs();
                LabelDump labelDump = new LabelDump();
                for (Integer integer : labelVAOs.keySet()) {
                    LabelVAO labelVAO = labelVAOs.get(integer);
                    labelDmp.data.put(integer,labelVAO.getFeatures());
                }
                OutputStream file = new FileOutputStream( dumpFilesDirectory+c+".dump" );
                OutputStream buffer = new BufferedOutputStream( file );
                ObjectOutput output = new ObjectOutputStream( buffer );
                output.writeObject(labelDump);

                output.close();
                  */
                brains.add(brainBuilder.getBrainVAO());
                System.out.println("Finished :"+c);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
        System.out.println("Finished reading all brains");
        System.out.println("Calculating Distances");
        List<List<Map<String, Map<String,Double>>>> distanceTensor = new ArrayList<List<Map<String, Map<String, Double>>>>();
        Set<String> featureSet;
        List<Integer> labels = new ArrayList<Integer>();
        {
            NodeVAO tmp = (NodeVAO) (brains.get(0).getNodeVAOs().toArray()[0]);
            featureSet = tmp.getFeatures().keySet();
        }


        for (int i = 0; i < brains.size(); i++) {
            System.out.println("brain:"+i);
            BrainVAO brain1 = brains.get(i);
            Object[] keys =  (brain1.getLabelVAOs().keySet().toArray());
            for (int j = 0; j < keys.length; j++) {
                System.out.println("\tfold:"+j);
                Integer key =(Integer) keys[j];
                LabelVAO labelVAO1 = brain1.getLabelVAOs().get(key);
                labels.add(key);
                ArrayList<Map<String, Map<String,Double>>> row = new ArrayList<Map<String, Map<String, Double>>>();
                for (int i2 = 0; i2 < brains.size(); i2++) {
                    System.out.println("\t\twith brain:"+i2);
                    BrainVAO brain2 = brains.get(i2);
                    Object[] keys2 =  (brain2.getLabelVAOs().keySet().toArray());
                    for (int j2 = 0; j2 < keys2.length; j2++) {
                        Object key2 = keys2[j2];
                        LabelVAO labelVAO2 = brain2.getLabelVAOs().get(key2);
                        HashMap<String, Map<String,Double>> cell = new HashMap<String, Map<String, Double>>();
                        for (String s : featureSet) {
                            HashMap<String, Double> distances = new HashMap<String, Double>();
                            Pair<List<Double>,List<Double>> probalitiesPair = getPairHistogram(labelVAO1.getNodeVAOs(),labelVAO2.getNodeVAOs(),s);
                            distances.put("KLD",getKLDivergence(probalitiesPair.getLeft(), probalitiesPair.getRight()));
                            distances.put("BHATTACHARYYA",getBHATTACHARYYADistance(probalitiesPair.getLeft(), probalitiesPair.getRight()));
                            distances.put("Bcoef",getBcoefDivergence(probalitiesPair.getLeft(), probalitiesPair.getRight()));

                            distances.put("CHI_SQUARE",getCHI_SQUAREDistance(probalitiesPair.getLeft(), probalitiesPair.getRight()));
                            distances.put("CITYBLOCK",getCITYBLOCKDistance(probalitiesPair.getLeft(), probalitiesPair.getRight()));
                            distances.put("EUCLIDEAN",getEUCLIDEANDistance(probalitiesPair.getLeft(), probalitiesPair.getRight()));
                            distances.put("JENSENSHANNON",getJensenShannonDivergence(probalitiesPair.getLeft(), probalitiesPair.getRight()));
                            distances.put("HELLINGER",getHellingerDivergence(probalitiesPair.getLeft(), probalitiesPair.getRight()));

                            cell.put(s, distances);
                        }
                        row.add(cell);
                    }

                }

                distanceTensor.add(row);
            }
        }

        System.out.println("distanceTensor is built");
        System.out.println("writing the distance Tensor to file:");
        OutputStream file = null;
        try {
            String name = new String();
            for (int ctr= 3; ctr< args.length;ctr++) {
                name = name + args[ctr]+",";
            }
            file = new FileOutputStream( dumpFilesDirectory+name+"-distanceTensor.dump" );
            OutputStream buffer = new BufferedOutputStream( file );
            ObjectOutput output = new ObjectOutputStream( buffer );
            output.writeObject(distanceTensor);

            output.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    private static double getJensenShannonDivergence(List<Double> p1, List<Double> p2) {


        ArrayList<Double> average = new ArrayList();

        for (int i = 0; i < p1.size(); ++i) {
            average.add((p1.get(i) + p2.get(i))/2.0);
        }

        return (getKLDivergence(p1,average) + getKLDivergence(p2,average))/2.0;
    }
    private static Double getEUCLIDEANDistance(List<Double> left, List<Double> right) {
        double ans = 0.0;

        for (int i = 0; i < left.size(); ++i) {
            ans = ans + Math.pow(left.get(i) - right.get(i), 2);
        }

        return ans;

    }

    private static Double getCITYBLOCKDistance(List<Double> left, List<Double> right) {
        double ans = 0.0;

        for (int i = 0; i < left.size(); ++i) {
            ans = ans + Math.abs(left.get(i) - right.get(i));
        }

        return ans;
    }

    private static Double getBHATTACHARYYADistance(List<Double> left, List<Double> right) {
        double bcoeff = 0.0;

        for (int i = 0; i < left.size(); ++i) {
            bcoeff = bcoeff + Math.sqrt(left.get(i) * right.get(i));
        }


        return -Math.log(bcoeff); // Distance
    }
    private static Double getCHI_SQUAREDistance(List<Double> left, List<Double> right) {
        double ans = 0.0;

        for (int i = 0; i < left.size(); ++i) {
            ans = ans + Math.pow(left.get(i) - right.get(i),2)/(left.get(i) + right.get(i));
        }


        return 0.5 * ans; // Distance
    }
    private static Double getBcoefDivergence(List<Double> left, List<Double> right) {
        double bcoeff = 0.0;

        for (int i = 0; i < left.size(); ++i) {
            bcoeff = bcoeff + Math.sqrt(left.get(i) * right.get(i));
        }


        return Math.sqrt(1 - bcoeff); // moved this division out of the loop -DM
    }

    private static Double getHellingerDivergence(List<Double> left, List<Double> right) {
        double ans = 0.0;

        for (int i = 0; i < left.size(); ++i) {
            ans = ans + Math.pow(Math.sqrt(left.get(i))-Math.sqrt(right.get(i)),2);
        }


        return Math.sqrt(ans/2.0); // moved this division out of the loop -DM
    }

    private static final double log2 = Math.log(2);
    private static final int bucketSize = 100;
    private static double getKLDivergence(List<Double> p1, List<Double> p2) {


        double klDiv = 0.0;

        for (int i = 0; i < p1.size(); ++i) {
            if (p1.get(i) == 0) { continue; }
            if (p2.get(i) == 0.0) { continue; } // Limin

            klDiv += p1.get(i) * Math.log( p1.get(i) / p2.get(i) );
        }

        return klDiv / log2; // moved this division out of the loop -DM
    }

    private static Pair<List<Double>,List<Double>> getPairHistogram(Set<NodeVAO> nodeVAOs1, Set<NodeVAO> nodeVAOs2, String s) {
        List<Pair<Double,Integer>> values = new ArrayList<Pair<Double, Integer>>();
        for (NodeVAO nodeVAO : nodeVAOs1) {
            values.add(new Pair<Double,Integer>(nodeVAO.getFeatures().get(s),1));
        }
        for (NodeVAO nodeVAO : nodeVAOs2) {
            values.add(new Pair<Double,Integer>(nodeVAO.getFeatures().get(s),2));
        }
        Collections.sort(values, new Comparator<Pair<Double, Integer>>() {
            @Override
            public int compare(Pair<Double, Integer> o1, Pair<Double, Integer> o2) {
                return o1.getLeft().compareTo(o2.getLeft());
            }
        });

        ArrayList<Integer> counts;
        List<Double> p1 = new ArrayList<Double>();
        List<Double> p2 = new ArrayList<Double>();

        int count =0;
        int index= 0;
        p1.add(Double.valueOf(0));
        p2.add(Double.valueOf(0));
        for (int i = 0; i < values.size(); i++) {
            Integer group = values.get(i).getRight();
            if(group == 1)
                p1.set(index,p1.get(index)+1);
            else
                p2.set(index, p2.get(index) + 1);
            if(++count>=bucketSize){
                count = 0;
                index++;
                p1.add(Double.valueOf(0));
                p2.add(Double.valueOf(0));
            }
        }
        for (int i = 0; i < p1.size(); i++) {
            p1.set(i,p1.get(i)/nodeVAOs1.size());
            p2.set(i,p2.get(i)/nodeVAOs2.size());

        }
        return new Pair<List<Double>, List<Double>>(p1,p2);
    }
}
