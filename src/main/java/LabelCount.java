import Classifier.*;
import Classifier.BrainVAO;
import Classifier.LabelVAO;
import Classifier.NodeVAO;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: koosha
 * Date: 5/3/13
 * Time: 6:56 PM
 * To change this template use File | Settings | File Templates.
 */


public class LabelCount {
    public static void main(String args[]){
        //ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        //BrainVAO brain = context.getBean("brain", BrainVAO.class);
        List<List<Integer>> labels= new ArrayList<List<Integer>>();



        String surfaceDirectory = new String(args[0] + "\\NKI-RS-22-");
        String dumpFilesDirectory = args[1] ;


        for (Integer c = 1; c<=34 ; c++){
            try {

                BrainVAO brain = new BrainVAO();
                BrainBuilder brainBuilder = new BrainBuilder();
                brainBuilder.setBrainVAO(brain);
                FileInputStream inputStream = new FileInputStream(surfaceDirectory+c+"\\affine_sulci.vtk");
                brainBuilder.setInputStream(inputStream);
                brainBuilder.build();
                System.out.println("made the base brain");
                System.out.println("Finished :"+c);
                List<Integer> row = new ArrayList<Integer>();
                System.out.println("brain "+c+", number of folds: "+brain.getLabelVAOs().size());
                row.addAll(brain.getLabelVAOs().keySet());
                labels.add(row);
                System.out.println(brain.getLabelVAOs().keySet());
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }

        System.out.println("Finished reading all brains");
        System.out.println("Calculating Distances");
        try {
            FileOutputStream file = new FileOutputStream( dumpFilesDirectory+"\\full-labels.dump" );
            OutputStream buffer = new BufferedOutputStream( file );
            ObjectOutput output = new ObjectOutputStream( buffer );
            output.writeObject(labels);

            output.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
