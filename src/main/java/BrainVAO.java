import java.util.HashSet;
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
    private Set<FoldVAO> foldVAOs;
    private Set<SurfaceVAO> surfaceVAOs;

    public BrainVAO() {
        this.nodeVAOs=new HashSet<NodeVAO>();
        this.foldVAOs=new HashSet<FoldVAO>();
        this.surfaceVAOs=new HashSet<SurfaceVAO>();
    }

    public BrainVAO(Set<NodeVAO> nodeVAOs, Set<FoldVAO> foldVAOs, Set<SurfaceVAO> surfaceVAOs) {
        this.nodeVAOs = nodeVAOs;
        this.foldVAOs = foldVAOs;
        this.surfaceVAOs = surfaceVAOs;
    }

    public Set<NodeVAO> getNodeVAOs() {
        return nodeVAOs;
    }

    public void setNodeVAOs(Set<NodeVAO> nodeVAOs) {
        this.nodeVAOs = nodeVAOs;
    }

    public Set<FoldVAO> getFoldVAOs() {
        return foldVAOs;
    }

    public void setFoldVAOs(Set<FoldVAO> foldVAOs) {
        this.foldVAOs = foldVAOs;
    }

    public Set<SurfaceVAO> getSurfaceVAOs() {
        return surfaceVAOs;
    }

    public void setSurfaceVAOs(Set<SurfaceVAO> surfaceVAOs) {
        this.surfaceVAOs = surfaceVAOs;
    }
}
