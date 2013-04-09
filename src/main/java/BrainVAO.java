import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: koosha
 * Date: 3/28/13
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class BrainVAO {
    private Set<NodeVAO> nodeVAOs;
    private Map<Integer,LabelVAO> LabelVAOs;
    private Set<SurfaceVAO> surfaceVAOs;

    public BrainVAO() {
        this.nodeVAOs=new HashSet<NodeVAO>();
        this.LabelVAOs =new HashMap<Integer,LabelVAO>();
        this.surfaceVAOs=new HashSet<SurfaceVAO>();
    }

    public BrainVAO(Set<NodeVAO> nodeVAOs, Map<Integer,LabelVAO> LabelVAOs, Set<SurfaceVAO> surfaceVAOs) {
        this.nodeVAOs = nodeVAOs;
        this.LabelVAOs = LabelVAOs;
        this.surfaceVAOs = surfaceVAOs;
    }

    public Set<NodeVAO> getNodeVAOs() {
        return nodeVAOs;
    }

    public void setNodeVAOs(Set<NodeVAO> nodeVAOs) {
        this.nodeVAOs = nodeVAOs;
    }

    public Map<Integer,LabelVAO> getLabelVAOs() {
        return LabelVAOs;
    }

    public void setLabelVAOs(Map<Integer,LabelVAO> LabelVAOs) {
        this.LabelVAOs = LabelVAOs;
    }

    public Set<SurfaceVAO> getSurfaceVAOs() {
        return surfaceVAOs;
    }

    public void setSurfaceVAOs(Set<SurfaceVAO> surfaceVAOs) {
        this.surfaceVAOs = surfaceVAOs;
    }
}
