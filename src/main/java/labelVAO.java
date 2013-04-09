import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: koosha
 * Date: 3/28/13
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class LabelVAO {
    private Set<NodeVAO> nodeVAOs;

    public Set<NodeVAO> getNodeVAOs() {
        return nodeVAOs;
    }

    public void setNodeVAOs(Set<NodeVAO> nodeVAOs) {
        this.nodeVAOs = nodeVAOs;
    }

    public int size() {
        return nodeVAOs.size();
    }

    public boolean isEmpty() {
        return nodeVAOs.isEmpty();
    }

    public boolean retainAll(Collection<?> c) {
        return nodeVAOs.retainAll(c);
    }

    public boolean addAll(Collection<? extends NodeVAO> c) {
        return nodeVAOs.addAll(c);
    }

    public Object[] toArray() {
        return nodeVAOs.toArray();
    }

    public boolean containsAll(Collection<?> c) {
        return nodeVAOs.containsAll(c);
    }

    public boolean contains(Object o) {
        return nodeVAOs.contains(o);
    }

    public boolean removeAll(Collection<?> c) {
        return nodeVAOs.removeAll(c);
    }

    public boolean remove(Object o) {
        return nodeVAOs.remove(o);
    }

    public <T> T[] toArray(T[] a) {
        return nodeVAOs.toArray(a);
    }

    public boolean add(NodeVAO nodeVAO) {
        return nodeVAOs.add(nodeVAO);
    }

    public void clear() {
        nodeVAOs.clear();
    }

    public Iterator<NodeVAO> iterator() {
        return nodeVAOs.iterator();
    }

    public LabelVAO() {
        nodeVAOs = new HashSet<NodeVAO>();
    }
}



