package Classifier;

import java.io.InputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: koosha
 * Date: 3/28/13
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */

public class BrainBuilder {
    private BrainVAO brainVAO;
    private InputStream inputStream;
    Map<Integer,LabelVAO> labelVAOs;
    ArrayList<NodeVAO> nodeVAOs;
    public BrainVAO getBrainVAO() {
        return brainVAO;
    }

    public void setBrainVAO(BrainVAO brainVAO) {
        this.brainVAO = brainVAO;
    }


    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    private void buildNodes(Scanner scanner,int nodeCounts){
        nodeVAOs = new ArrayList<NodeVAO>(nodeCounts);
        for (int i = 0; i < nodeCounts; i++) {
            String line = scanner.nextLine();
            nodeVAOs.add(new NodeVAO(line));
        }
    }
    public void build(){
        Scanner scanner;
        scanner = new Scanner(inputStream);
        String line = new String();
        for (int i = 0; i < 5; i++) {
            line = scanner.nextLine();
        }
        String[] split = line.split(" ");
        int nodeCounts = Integer.parseInt(split[1]);
        buildNodes(scanner, nodeCounts);
        System.out.println("Ignoring surfaces");
        do{
            line = scanner.nextLine();
        } while(!line.contains("POINT_DATA"));
        System.out.println("after surfaces");
        split = line.split(" ");
        int labelCount = Integer.parseInt(split[1]);
        System.out.println(labelCount+" number labels");
        scanner.nextLine();scanner.nextLine();
        buildLabels(scanner, labelCount);
        brainVAO.setLabelVAOs(labelVAOs);
        brainVAO.setNodeVAOs(new HashSet<NodeVAO>(nodeVAOs));
    }
    public void addNewFeatureFile(InputStream inputStream,String featurename){
        Scanner scanner;
        scanner = new Scanner(inputStream);
        String line;
        try{
            do{
                line = scanner.nextLine();
            } while(!line.contains("POINT_DATA"));
            String[] split = line.split(" ");
            int featureCount = Integer.parseInt(split[1]);
            if(featureCount!=nodeVAOs.size())
                throw new Exception("Size didn't matched");
            scanner.nextLine();
            scanner.nextLine();
            for(int i= 0 ; i < featureCount;i++){
                double feature = scanner.nextDouble();
                nodeVAOs.get(i).put(featurename,feature);
            }

            brainVAO.setLabelVAOs(labelVAOs);
            brainVAO.setNodeVAOs(new HashSet<NodeVAO>(nodeVAOs));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void buildLabels(Scanner scanner, int labelCount) {
        labelVAOs = new HashMap<Integer, LabelVAO>(labelCount);
        for (int i = 0; i < labelCount; i++) {
            String line = scanner.nextLine();
            int label = (int) Double.parseDouble(line);
            if(!labelVAOs.containsKey(label)){
                labelVAOs.put(label,new LabelVAO());
            }
            labelVAOs.get(label).add(nodeVAOs.get(i));

        }
    }


    public void computeFeatures() {
        BrainVAO brainObj=brainVAO;
        for (LabelVAO labelVAO : brainObj.getLabelVAOs().values()) {
            HashMap<String,ArrayList<Double>> featureMap = new HashMap<String, ArrayList<Double>>();
            for (NodeVAO nodeVAO : labelVAO.getNodeVAOs()) {
                for (String s : nodeVAO.getFeatures().keySet()) {
                    Double d = nodeVAO.getFeatures().get(s);
                    if(!featureMap.containsKey(s)){
                        featureMap.put(s,new ArrayList<Double>());
                    }
                    featureMap.get(s).add(d);
                }
            }
            for (String s : featureMap.keySet()) {
                ArrayList<Double> values = featureMap.get(s);
                Map<String, Double> features = moments(values,s);
                labelVAO.getFeatures().putAll(features);
            }
        }
    }

    public Map<String, Double> moments(ArrayList<Double> nums,String s) {
        Map<String, Double> features = new HashMap<String, Double>();
        double sum = 0.0;
        double mean = 0.0;
        double average_deviation = 0.0;
        double standard_deviation = 0.0;
        double variance = 0.0;
        double skew = 0.0;
        double kurtosis = 0.0;
        double median = 0.0;
        double deviation = 0.0;
        int n, mid = 0;

        for (int iter = 0; iter < nums.size(); iter++) {
            sum += nums.get(iter);
        }
        n = nums.size();
        mean = sum/n;
        for (int i=0; i<n; i++) {
            deviation = ((Double)nums.get(i)).doubleValue() - mean;
            average_deviation += Math.abs(deviation);
            variance += Math.pow(deviation,2);
            skew += Math.pow(deviation,3);
            kurtosis += Math.pow(deviation,4);
        }
        average_deviation /= n;
        variance /= (n - 1);
        standard_deviation = Math.sqrt(variance);
        ArrayList<Double> moment = new ArrayList<Double>();
        if (variance != 0.0) {
            skew /= (n * variance * standard_deviation);
            kurtosis /= (n * variance * variance) - 3.0;
            for(int iter = 5; iter < 10; iter++){
                Double deviation1 = 0.0;
                Double average_deviation1 = 0.0;
                for (int iter1 = 1; iter1 < n; iter1++) {
                    deviation1 = Math.pow(((Double)nums.get(iter1)).doubleValue() - mean,iter);
                    average_deviation1 += deviation1;
                }
                moment.add((average_deviation1/n)/Math.pow(deviation,iter));
            }
        }

        Collections.sort(nums);

        mid = (n/2);
        median = (n % 2 != 0) ?
                ((Double)nums.get(mid)).doubleValue() :
                (((Double)nums.get(mid)).doubleValue() +
                        ((Double)nums.get(mid-1)).doubleValue())/2;

        features.put(s+"#median",median);
        features.put(s+"#mean",mean);
        features.put(s+"#average deviation",average_deviation);
        features.put(s+"#standard deviation",standard_deviation);
        features.put(s+"#variance",variance);
        features.put(s+"#skew",skew);
        features.put(s+"#kurtosis",kurtosis);
        for (int iter = 0; iter < moment.size(); iter++) {
            features.put(s+"#moment"+(iter+5),moment.get(iter));
        }
        return features;
    }
}