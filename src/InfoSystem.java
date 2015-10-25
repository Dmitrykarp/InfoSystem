import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

public class InfoSystem {
    public static void main(String[] args) throws DatatypeConfigurationException, JAXBException {
        Model thisModel = new Model();
        View thisView = new View();
        Controller thisController = new Controller(thisModel, thisView);

        try {
            thisController.run();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
