import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: koosha
 * Date: 4/10/13
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class LabelDump implements Serializable {
    Map<Integer,Map<String,Double>> data;

    public LabelDump(Map<Integer, Map<String, Double>> data) {
        this.data = data;
    }

    public LabelDump() {
        data = new HashMap<Integer, Map<String, Double>>();
    }
}
