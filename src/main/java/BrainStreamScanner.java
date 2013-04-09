import java.io.InputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: koosha
 * Date: 3/28/13
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class BrainStreamScanner {
    private BrainVAO brainVAO;
    private InputStream inputStream;
    Map<Integer,LabelVAO> labelVAOs;
    ArrayList<NodeVAO> nodeVAOs;
    ArrayList<SurfaceVAO> surfaceVAOs;
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

        line = scanner.nextLine();
        split = line.split(" ");
        int srufaceCount = Integer.parseInt(split[1]);
        buildSrufaces(scanner,srufaceCount);

        line = scanner.nextLine();
        split = line.split(" ");
        int labelCount = Integer.parseInt(split[1]);
        scanner.nextLine();line = scanner.nextLine();
        buildLabels(scanner, labelCount);
        brainVAO.setLabelVAOs(labelVAOs);
        brainVAO.setSurfaceVAOs(new HashSet<SurfaceVAO>(surfaceVAOs));
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
            brainVAO.setSurfaceVAOs(new HashSet<SurfaceVAO>(surfaceVAOs));
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
            nodeVAOs.get(i).setLabelVAO(labelVAOs.get(label));
            labelVAOs.get(label).add(nodeVAOs.get(i));

        }
    }


    private void buildSrufaces(Scanner scanner, int srufaceCount) {
        surfaceVAOs = new ArrayList<SurfaceVAO>(srufaceCount);
        for (int i = 0; i < srufaceCount; i++) {
            String line = scanner.nextLine();
            String[] strings = line.split(" ");

            SurfaceVAO surfaceVAO = new SurfaceVAO();
            for (int j = 1; j <= Integer.parseInt(strings[0]); j++) {
                String string = strings[j];
                int node = Integer.parseInt(string);
                surfaceVAO.add(nodeVAOs.get(node));
                nodeVAOs.get(node).getSurfaceVAOs().add(surfaceVAO);
            }
            surfaceVAOs.add(surfaceVAO);
        }
    }
}
