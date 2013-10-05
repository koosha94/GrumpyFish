package LabelUtilities;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: koosha
 * Date: 8/27/13
 * Time: 9:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class SulciMapper {
    public static void main(String []args){

        if(args.length !=4){
            System.err.println("you have to enter 3 arguments!!");
        }
        String labelFileName = new String(args[0]);
        String surfaceDirectory = new String(args[1] + "\\NKI-RS-22-");
        String outputDirectory = new String(args[2]+"\\brain_");
        Integer brainNumber = Integer.parseInt(args[3]);


        try {
            InputStream labelFile = new FileInputStream( labelFileName);
            InputStream labelBuffer = new BufferedInputStream( labelFile );
            ObjectInput labelInput = new ObjectInputStream( labelBuffer );
            List<List<Integer>> labels = (List<List<Integer>>) (labelInput.readObject());

            for(brainNumber = 24; brainNumber<35;brainNumber++){

                FileInputStream inputStream = new FileInputStream(surfaceDirectory+brainNumber+"\\affine_sulci.vtk");
            Scanner scanner;
            scanner = new Scanner(inputStream);
            PrintWriter out = new PrintWriter(new FileWriter(outputDirectory+brainNumber+"_remapped_sulci.vtk"));
            String line;
            do{
                line = scanner.nextLine();
                out.println(line);
            } while(!line.contains("POINT_DATA"));

            String[] split = line.split(" ");
            int labelCount = Integer.parseInt(split[1]);
            System.out.println(labelCount+" number labels");
            out.println(scanner.nextLine());
            out.println(scanner.nextLine());
            for(int i =0; i < labelCount ; i++){
                line = scanner.nextLine();
                int label = (int) Double.parseDouble(line);
                out.println(labels.get(brainNumber-1).get(label+1)-1);
            }

            out.close();
            scanner.close();
            inputStream.close();

            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
