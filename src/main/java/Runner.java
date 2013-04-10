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
            FileInputStream inputStream = new FileInputStream("folds.vtk.txt");
            brainBuilder.setInputStream(inputStream);
            brainBuilder.build();
            inputStream = new FileInputStream("feature1.vtk.txt");
            brainBuilder.addNewFeatureFile(inputStream,"mincurv");
            BrainVAO brainVAO = brainBuilder.getBrainVAO();
            brainBuilder.computeFeatures(brainVAO);
            System.out.println("goool");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
