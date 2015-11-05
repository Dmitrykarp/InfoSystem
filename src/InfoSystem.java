import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Точка входа.
 *
 * @author Karpenko Dmitry
 */
public class InfoSystem {
    public static void main(String[] args) throws IOException, JAXBException {
        Model thisModel = new Model();
        View thisView = new View();
        Controller thisController = new Controller(thisModel, thisView);

        thisController.run();
    }
}
