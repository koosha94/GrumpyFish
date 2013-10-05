import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: koosha
 * Date: 3/21/13
 * Time: 7:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Runner {

    public static void main(String args[]){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        BrainStreamScanner brainBuilder = context.getBean("brainBuilder", BrainStreamScanner.class);
        BrainVAO brain = context.getBean("brain", BrainVAO.class);
        brainBuilder.setBrainVAO(brain);
        Integer c = 22;
        String surfaceDirectory = new String("D:\\mindboggle\\Mindboggle101_surfaces\\NKI-RS-22_surfaces\\NKI-RS-22-");
        String measuresDirectory = new String("D:\\mindboggle\\Mindboggle101_surfaces\\Final Mindboggle Measures\\_hemi_lh_subject_NKI-RS-22-");
        String dumpFilesDirectory = new String("D:\\mindboggle\\dumpfiles\\");

        try {
            FileInputStream inputStream = new FileInputStream(surfaceDirectory+c+"\\lh.labels.DKT31.manual.vtk");
            brainBuilder.setInputStream(inputStream);
            brainBuilder.build();
            String gradstr = new String("");
            for(int grad = 0 ; grad <3 ; grad++) {
                inputStream = new FileInputStream(measuresDirectory+c+"\\"+gradstr+"lh.pial.area.vtk");
                brainBuilder.addNewFeatureFile(inputStream,gradstr+"pial.area");
                inputStream = new FileInputStream(measuresDirectory+c+"\\"+gradstr+"lh.pial.curv.avg.vtk");
                brainBuilder.addNewFeatureFile(inputStream,gradstr+"pial.curv.avg");
                inputStream = new FileInputStream(measuresDirectory+c+"\\"+gradstr+"lh.pial.curv.gauss.vtk");
                brainBuilder.addNewFeatureFile(inputStream,gradstr+"pial.curv.guass");
                inputStream = new FileInputStream(measuresDirectory+c+"\\"+gradstr+"lh.pial.depth.vtk");
                brainBuilder.addNewFeatureFile(inputStream,gradstr+"pial.depth");
                inputStream = new FileInputStream(measuresDirectory+c+"\\"+gradstr+"lh.pial.curv.max.vtk");
                brainBuilder.addNewFeatureFile(inputStream,gradstr+"pial.curv.max");
                inputStream = new FileInputStream(measuresDirectory+c+"\\"+gradstr+"lh.pial.curv.min.vtk");
                brainBuilder.addNewFeatureFile(inputStream,gradstr+"pial.curv.min");
                inputStream = new FileInputStream(measuresDirectory+c+"\\"+gradstr+"sulc.vtk");
                brainBuilder.addNewFeatureFile(inputStream,gradstr+"sulc");
                gradstr = gradstr + "gradient_";
            }
            brainBuilder.computeFeatures();
            BrainVAO brainVAO = brainBuilder.getBrainVAO();
            Map<Integer,LabelVAO> labelVAOs = brainVAO.getLabelVAOs();
            LabelDump labelDump = new LabelDump();
            for (Integer integer : labelVAOs.keySet()) {
                LabelVAO labelVAO = labelVAOs.get(integer);
                labelDump.data.put(integer,labelVAO.getFeatures());
            }
            OutputStream file = new FileOutputStream( dumpFilesDirectory+c+".dump" );
            OutputStream buffer = new BufferedOutputStream( file );
            ObjectOutput output = new ObjectOutputStream( buffer );
            output.writeObject(labelDump);
            output.close();
            System.out.println("Finished :)");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
