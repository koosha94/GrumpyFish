/**
 * Created with IntelliJ IDEA.
 * User: koosha
 * Date: 3/28/13
 * Time: 6:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeVAO {
    Double X,Y,Z;
    SurfaceVAO surfaceVAO;
    public NodeVAO() {
    }

    public NodeVAO(Double x, Double y, Double z) {
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
