package Classifer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: koosha
 * Date: 5/3/13
 * Time: 5:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class doubleTensorBuidler {
    final static int datacount = 34;
    final static int maxFoldPerBrain = 30;
    public static void main(String []args){
        //List<List<Map<String, Map<String,Double>>>> distanceTensor = new ArrayList<List<Map<String, Map<String, Double>>>>();
        try {

            List<List<Integer>> labels;
            List<List<List<Double>>> fullDump = new ArrayList<List<List<Double>>>();
            {
                InputStream labelFile = new FileInputStream( "C:\\brain data\\MindboggleData\\dump_datas\\full-labels.dump" );
                InputStream labelBuffer = new BufferedInputStream( labelFile );
                ObjectInput labelInput = new ObjectInputStream( labelBuffer );
                labels = (List<List<Integer>>)(labelInput.readObject());
                labelInput.close();
                labelBuffer.close();
                labelFile.close();
            }

            for (int i = 0; i <maxFoldPerBrain * datacount ; i++) {
                ArrayList<List< Double>> row = new ArrayList<List<Double>>();
                for (int j = 0; j < maxFoldPerBrain * datacount; j++){
                  row.add(new ArrayList<Double>());
                }
                fullDump.add(row);
            }
            List<String> featureLabels= new ArrayList<String>();

            for (int i = 0; i < datacount; i++) {
                for(int j = i+1;j<datacount;j++)  {
                    InputStream file = null;
                    file = new FileInputStream( "C:\\brain data\\MindboggleData\\dump_datas\\"+(i+1)+","+(j+1)+",-distanceTensor.dump" );
                    InputStream buffer = new BufferedInputStream( file );
                    ObjectInput input = new ObjectInputStream( buffer );
                    List<List<Map<String, Map<String,Double>>>> distanceTensor = (List<List<Map<String, Map<String,Double>>>>)(input.readObject());
                    for (int k = 0; k < distanceTensor.size(); k++) {
                        List<Map<String, Map<String, Double>>> row = distanceTensor.get(k);
                        for (int l = 0; l <distanceTensor.size(); l++) {
                            Map<String, Map<String, Double>> stringMapMap = row.get(l);
                            List<Double> distanceCell = new ArrayList<Double>();
                            /*if(k==0&&l==0&&i==0&&j==1)
                                featureLabels.addAll(stringMapMap.keySet()); */
                            for (String featureName : stringMapMap.keySet()) {
                                Map<String, Double> stringDoubleMap = stringMapMap.get(featureName);
                                for (String divergenceMethod : stringDoubleMap.keySet()) {
                                    distanceCell.add(stringDoubleMap.get(divergenceMethod));

                                }
                            }

                            Integer iSize = labels.get(i).size();
                            if(k<iSize&&l<iSize){
                                fullDump.get(maxFoldPerBrain*i+labels.get(i).get(k)+1).set(maxFoldPerBrain*i+labels.get(i).get(l)+1,distanceCell);
                            }
                            else if(k<iSize&&l>=iSize){
                                fullDump.get(maxFoldPerBrain*i+labels.get(i).get(k)+1).set(maxFoldPerBrain*j+labels.get(j).get(l-iSize)+1,distanceCell);
                            }
                            else if(k>=iSize&&l<iSize){
                                fullDump.get(maxFoldPerBrain*j+labels.get(j).get(k-iSize)+1).set((maxFoldPerBrain*i+labels.get(i).get(l))+1,distanceCell);
                            }
                            else{
                                fullDump.get(maxFoldPerBrain*j+labels.get(j).get(k-iSize)+1).set((maxFoldPerBrain*j+labels.get(j).get(l-iSize))+1,distanceCell);
                            }

                        }
                    }
                    input.close();
                    buffer.close();
                    file.close();
                    System.out.println("finshed:"+i+","+j);
                }
            }
            System.out.println("finshed");
            FileOutputStream file = new FileOutputStream( "C:\\brain data\\MindboggleData\\dump_datas\\full-doubleTensor.dump" );
            OutputStream buffer = new BufferedOutputStream( file );
            ObjectOutput output = new ObjectOutputStream( buffer );
            output.writeObject(fullDump);

            output.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
}

