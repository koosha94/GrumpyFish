import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
        try {
            FileInputStream inputStream = new FileInputStream("VTK/brain/NKI-RS-22-1/lh.labels.DKT31.manual.vtk");
            brainBuilder.setInputStream(inputStream);
            brainBuilder.build();
            inputStream = new FileInputStream("VTK/measures/_hemi_lh_subject_NKI-RS-22-1/lh.pial.area.vtk");
            brainBuilder.addNewFeatureFile(inputStream,"pial.area");
            inputStream = new FileInputStream("VTK/measures/_hemi_lh_subject_NKI-RS-22-1/lh.pial.curv.avg.vtk");
            brainBuilder.addNewFeatureFile(inputStream,"pial.curv");
            inputStream = new FileInputStream("VTK/measures/_hemi_lh_subject_NKI-RS-22-1/lh.pial.curv.gauss.vtk");
            brainBuilder.addNewFeatureFile(inputStream,"pial.curv.guass");
            inputStream = new FileInputStream("VTK/measures/_hemi_lh_subject_NKI-RS-22-1/lh.pial.depth.vtk");
            brainBuilder.addNewFeatureFile(inputStream,"pial.depth");
            inputStream = new FileInputStream("VTK/measures/_hemi_lh_subject_NKI-RS-22-1/thickness.vtk");
            brainBuilder.addNewFeatureFile(inputStream,"thickness");
            brainBuilder.computeFeatures();
            System.out.println("goool");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
