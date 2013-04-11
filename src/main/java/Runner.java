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
        Integer c = 9;
        try {
            FileInputStream inputStream = new FileInputStream("VTK/brain/NKI-RS-22-"+c+"/lh.labels.DKT31.manual.vtk");
            brainBuilder.setInputStream(inputStream);
            brainBuilder.build();
            inputStream = new FileInputStream("VTK/measures/_hemi_lh_subject_NKI-RS-22-"+c+"/lh.pial.area.vtk");
            brainBuilder.addNewFeatureFile(inputStream,"pial.area");
            inputStream = new FileInputStream("VTK/measures/_hemi_lh_subject_NKI-RS-22-"+c+"/lh.pial.curv.avg.vtk");
            brainBuilder.addNewFeatureFile(inputStream,"pial.curv");
            inputStream = new FileInputStream("VTK/measures/_hemi_lh_subject_NKI-RS-22-"+c+"/lh.pial.curv.gauss.vtk");
            brainBuilder.addNewFeatureFile(inputStream,"pial.curv.guass");
            inputStream = new FileInputStream("VTK/measures/_hemi_lh_subject_NKI-RS-22-"+c+"/lh.pial.depth.vtk");
            brainBuilder.addNewFeatureFile(inputStream,"pial.depth");
            inputStream = new FileInputStream("VTK/measures/_hemi_lh_subject_NKI-RS-22-"+c+"/thickness.vtk");
            brainBuilder.addNewFeatureFile(inputStream,"thickness");
            brainBuilder.computeFeatures();
            BrainVAO brainVAO = brainBuilder.getBrainVAO();
            Map<Integer,LabelVAO> labelVAOs = brainVAO.getLabelVAOs();
            LabelDump labelDump = new LabelDump();
            for (Integer integer : labelVAOs.keySet()) {
                LabelVAO labelVAO = labelVAOs.get(integer);
                labelDump.data.put(integer,labelVAO.getFeatures());
            }
            OutputStream file = new FileOutputStream( "objfile/lh"+c+".dump" );
            OutputStream buffer = new BufferedOutputStream( file );
            ObjectOutput output = new ObjectOutputStream( buffer );
            output.writeObject(labelDump);
            output.close();
            System.out.println("goool");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
