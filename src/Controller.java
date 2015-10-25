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


    public void run() throws DatatypeConfigurationException, JAXBException {
       /* thisModel.addGroup(11, "Bio");
        thisModel.addStudent("Тест", "Тестов", "Тестович", "2015.15.15");
        thisModel.studentToGroup(1, 11);
        thisModel.addStudent("Тест2", "Тестов2", "Тестович2", "2001.08.25");
        thisModel.studentToGroup(2, 11);

        thisModel.addGroup(12, "KNIIT");
        thisModel.addStudent("Дмитрий", "Карпенко", "Иванович", "2015.10.19");
        thisModel.studentToGroup(3, 12);
        thisModel.addStudent("Ксения", "Карпенко", "Андреевна", "2015.10.20");
        thisModel.studentToGroup(4, 12);
        thisModel.addStudent("Андрей", "Казаков", "Александрович", "2010.11.29");
        thisModel.studentToGroup(5, 12);


      */  thisModel.loadXML("C:\\test.xml", thisModel);
        System.out.println(thisModel.getGroup());
        thisModel.saveXML("C:\\ff.xml");


    }


    public ArrayList<String> parseCommand(String s) {
        return null;
    }

}
