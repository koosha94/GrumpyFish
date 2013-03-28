import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: koosha
 * Date: 3/28/13
 * Time: 6:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class SurfaceVAO {
    double surfaceNumber;
    Set<NodeVAO> nodeVAOs;

    public SurfaceVAO() {
        surfaceNumber=new Double(-1);
        nodeVAOs = new HashSet<NodeVAO>();
    }

    public double getSurfaceNumber() {
        return surfaceNumber;
    }

    public void setSurfaceNumber(double surfaceNumber) {
        this.surfaceNumber = surfaceNumber;
    }

    public Set<NodeVAO> getNodeVAOs() {
        return nodeVAOs;
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
}
