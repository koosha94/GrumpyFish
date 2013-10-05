package KNN;


import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: koosha
 * Date: 5/3/13
 * Time: 11:22 PM
 * To change this template use File | Settings | File Templates.
 */
class Pair<L,R> {

    private final L left;
    private final R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() { return left; }
    public R getRight() { return right; }

    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        return this.left.equals(pairo.getLeft()) &&
                this.right.equals(pairo.getRight());
    }

}
public class Main {
    final static int datacount = 34;
    final static int maxFoldPerBrain = 30;
    final static int kNNFactor = 15;
    public static List<Integer> findKNN(List<List<Double>> distances, Integer K){
        List<Pair<Double,Integer>> distanceeArray = new ArrayList<Pair<Double, Integer>>();
        for (int i = 0; i < distances.size(); i++) {
            List<Double> doubles = distances.get(i);
            double res = 0;
            for (Double aDouble : doubles) {
                res = res + aDouble*aDouble;
            }
            if(doubles.size()!=0)
                distanceeArray.add(new Pair<Double,Integer>(res,i));

        }
        Collections.sort(distanceeArray, new Comparator<Pair<Double, Integer>>() {
            @Override
            public int compare(Pair<Double, Integer> o1, Pair<Double, Integer> o2) {
                return o1.getLeft().compareTo(o2.getLeft());
            }
        });
        List<Integer> answer = new ArrayList<Integer>();
        for (int i = 0; i < K && i < distanceeArray.size(); i++) {
             answer.add(distanceeArray.get(i).getRight());
        }
        return answer;
    }
    public static void main(String[] args){
        try {

            List<List<List<Double>>> distances;
            {
                InputStream distanceFile = new FileInputStream( "C:\\brain data\\MindboggleData\\dump_datas\\full-doubleTensor.dump" );
                InputStream distanceBuffer = new BufferedInputStream( distanceFile );
                ObjectInput distanceInput = new ObjectInputStream( distanceBuffer );
                distances = (List<List<List<Double>>>)(distanceInput.readObject());
                distanceInput.close();
                distanceBuffer.close();
                distanceFile.close();
            }
            int error = 0;
            int total = 0;
            List<Integer> errors = new ArrayList<Integer>();

            for(int j = 0 ; j < 30 ; j ++)    {
                errors.add(0);
            }

            List<Integer> totals = new ArrayList<Integer>();

            for(int j = 0 ; j < 30 ; j ++)    {
                totals.add(0);
            }
            for (int i = 0; i < distances.size(); i++) {
                List<List<Double>> lists = distances.get(i);
                List<Integer> knn = findKNN(lists,kNNFactor);
                if(knn.size() == 0)
                    continue;
                total ++;
                totals.set(i%40,totals.get(i%40)+1);
                knn.remove((Object)i);
                List<Integer> buckets = new ArrayList<Integer>();

                for(int j = 0 ; j < 40 ; j ++)    {
                    buckets.add(0);
                }
                for(int j = 0 ; j <knn.size();j++){
                    Integer integer = buckets.get(knn.get(j) % 40);
                    buckets.set(knn.get(j) % 40,integer+1);
                }
                int estimadedLabel = buckets.indexOf(Collections.max(buckets));
                        System.out.println(i + " : " + estimadedLabel);
                if(i%40!=estimadedLabel ){
                    error++;
                    errors.set(i%40,errors.get(i%40)+1);
                }


            }
            for(int i = 0 ; i < totals.size();i++){
                if(totals.get(i)!=0)
                    System.out.println("classification error of region( " +i+ " )is : "+(((double)errors.get(i))/((double)totals.get(i))));
            }
            System.out.println("total error is : "+(((double)error)/((double)total)));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
