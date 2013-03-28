import java.io.InputStream;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: koosha
 * Date: 3/28/13
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class BrainStreamScanner {
    private Scanner scanner;
    private BrainVAO brainVAO;
    private InputStream inputStream;

    public BrainVAO getBrainVAO() {
        return brainVAO;
    }

    public void setBrainVAO(BrainVAO brainVAO) {
        this.brainVAO = brainVAO;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

}
