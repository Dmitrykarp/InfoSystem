import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;


public class Controller {
    private Model thisModel;
    private View thisView;


    Controller(Model model, View view) {
        thisModel = model;
        thisView = view;
    }


    public void run() throws DatatypeConfigurationException, JAXBException, SAXException {



        thisModel= thisModel.loadXML("C:\\test.xml", thisModel);

        thisModel.saveXML("C:\\ff.xml");


    }


    public ArrayList<String> parseCommand(String s) {
        return null;
    }

}
