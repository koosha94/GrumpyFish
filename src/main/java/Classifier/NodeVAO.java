package Classifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: koosha
 * Date: 3/28/13
 * Time: 6:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeVAO {
    private Double X,Y,Z;
    private Map<String,Double> features;

    public Map<String, Double> getFeatures() {
        return features;
    }

    public void setFeatures(Map<String, Double> features) {
        this.features = features;
    }

    public Double put(String key, Double value) {
        return features.put(key, value);
    }

    public Double get(Object key) {
        return features.get(key);
    }

    public boolean containsKey(Object key) {
        return features.containsKey(key);
    }


    public NodeVAO() {
        features= new HashMap<String, Double>();
    }
    public void setFromString(String line){
        String[] split = line.split(" ");
        setX(Double.parseDouble(split[0]));
        setY(Double.parseDouble(split[1]));
        setZ(Double.parseDouble(split[2]));
    }

    public NodeVAO(String line){

        features= new HashMap<String, Double>();
        setFromString(line);
    }
    public NodeVAO(Double x, Double y, Double z) {

        features= new HashMap<String, Double>();
        X = x;
        Y = y;
        Z = z;
    }

    public Double getX() {
        return X;
    }

    public void setX(Double x) {
        X = x;
    }

    public Double getY() {
        return Y;
    }

    public void setY(Double y) {
        Y = y;
    }

    public Double getZ() {
        return Z;
    }

    public void setZ(Double z) {
        Z = z;
    }
}
