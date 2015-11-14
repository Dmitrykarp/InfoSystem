import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.text.ParseException;

/**
 * Точка входа.
 *
 * @author Karpenko Dmitry
 */
public class InfoSystem {
    public static void main(String[] args) throws IOException, JAXBException, ParseException {
        Model thisModel = new Model();
        View thisView = new View();
        ControllerNew thisController = new ControllerNew(thisModel, thisView);

        thisController.run();
    }
}
