import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.*;
import weka.classifiers.functions.*;
import weka.classifiers.lazy.KStar;
import weka.classifiers.lazy.LBR;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.ADTree;
import weka.classifiers.trees.Id3;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.NBTree;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: koosha
 * Date: 4/10/13
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class Learner {
    public static void main(String args[]) throws Exception {
        for(int datacount = 10 ; datacount<=22;datacount = datacount+3) {
        List<LabelDump> labelDumps = new ArrayList<LabelDump>();
        try {
            for (int i = 1; i <= datacount; i++) {
                InputStream file = new FileInputStream( "objfile/lh"+i+".dump" );
                InputStream buffer = new BufferedInputStream( file );
                ObjectInput input = new ObjectInputStream( buffer );
                labelDumps.add((LabelDump)input.readObject());

            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Map<Integer,Map<String,Double>> tmp = labelDumps.get(1).data;
        Map<String, Double> stringDoubleMap = (Map<String, Double>) tmp.values().toArray()[0];
        FastVector fvWekaAttributes = new FastVector(stringDoubleMap.size()+1);
        for (String s : stringDoubleMap.keySet()) {
            fvWekaAttributes.addElement(new Attribute(s));
        }
        Set<Integer> labels = new HashSet<Integer>();
        for (LabelDump labelDump : labelDumps) {
            for (Integer integer : labelDump.data.keySet()) {
                labels.add(integer);
            }
        }


        FastVector fvClassVal = new FastVector(labels.size());
        for (Integer label : labels) {
            fvClassVal.addElement(label.toString());
        }
        Attribute ClassAttribute = new Attribute("theClass", fvClassVal);
        fvWekaAttributes.addElement(ClassAttribute);

        Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 22);
        for (LabelDump labelDump : labelDumps) {
            Map<Integer, Map<String, Double>> data = labelDump.data;
            for (Integer integer : data.keySet()) {

                Map<String, Double> features = data.get(integer);

                Instance iExample = new Instance(stringDoubleMap.size()+1);
                for (int i = 0; i < features.keySet().toArray().length; i++) {
                    iExample.setValue((Attribute)fvWekaAttributes.elementAt(i),features.get(features.keySet().toArray()[i]));
                }
                iExample.setValue((Attribute)fvWekaAttributes.elementAt(stringDoubleMap.size()), integer.toString());
                //System.out.println(iExample);
                isTrainingSet.add(iExample);
            }
        }

        isTrainingSet.setClassIndex(stringDoubleMap.size());





        double error = 0;


        Random rand = new Random(3);   // create seeded number generator
        Instances randData = new Instances(isTrainingSet);// create copy of original data
        randData.randomize(rand);
        int foldsCount = 10;
        randData.stratify(foldsCount);





        for (int n = 0; n < foldsCount; n++) {
            Instances train = randData.trainCV(foldsCount, n);
            Instances test = randData.testCV(foldsCount, n);


            Classifier cModel = (Classifier)new Logistic();
            ((Logistic)cModel).setMaxIts(20);
            cModel.buildClassifier(train);
            Evaluation eTest = new Evaluation(train);
            eTest.evaluateModel(cModel, test);
            error  = error + eTest.errorRate();
// Print the result à la Weka explorer:
            String strSummary = eTest.toSummaryString();
            //System.out.println(strSummary);

// Get the confusion matrix
            double[][] cmMatrix = eTest.confusionMatrix();
            /*for(int row_i=0; row_i<cmMatrix.length; row_i++){
                for(int col_i=0; col_i<cmMatrix[row_i].length; col_i++){
                    System.out.print(cmMatrix[row_i][col_i]);
                    System.out.print("|");
                }
                System.out.println();
            }      */

        }
        System.out.println("for "+datacount+"data, AVG Error is :"+error/foldsCount);

    }

    }
}
