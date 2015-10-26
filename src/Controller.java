import org.xml.sax.SAXException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class Controller {
    private Model thisModel;
    private View thisView;


    Controller(Model model, View view) {
        thisModel = model;
        thisView = view;
    }


    public void run() throws DatatypeConfigurationException, JAXBException, SAXException, IOException {
        String [] command;
        String exit=" ";
        Boolean confirm;

        thisModel = thisModel.loadXML("src\\xml\\test.xml", thisModel);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (!exit.equals("exit")) {
            thisView.printConsole();
            command = parseCommand(reader.readLine());
            switch (command[0]){
                case "add":
                    if(command[1].equals("-s")){
                        confirm=thisModel.addStudent(command[2],command[4], command[3],command[5]);
                        thisView.printConfirm(confirm, command[1]);
                    }else if(command[1].equals("-g")){
                        try {
                            confirm=thisModel.addGroup(Integer.parseInt(command[2]),command[3]);
                            thisView.printConfirm(confirm, command[1]);
                        }catch (NumberFormatException e){
                            thisView.printError();
                        }
                    }else if(command[1].equals("-stg")){
                        try {
                            confirm=thisModel.studentToGroup(Integer.parseInt(command[2]),Integer.parseInt(command[3]));
                            thisView.printConfirm(confirm, command[1]);
                        }catch (NumberFormatException e){
                            thisView.printError();
                        }
                    }else thisView.printError();
                    break;
                case "del":
                    break;
                case "mod":
                    break;
                case "view":
                    if(command[1].equals("-alls")) {
                        thisView.printAllStudent(thisModel.getStudent());
                    }else if(command[1].equals("-allg")) {
                        thisView.printAllGroup(thisModel.getGroup());
                    }else if(command[1].equals("-s")){
                        try {
                            thisView.printStudent(thisModel.getStudent(Integer.parseInt(command[2])-1));
                        }catch (NumberFormatException e){
                            thisView.printError();
                        }
                    }else if(command[1].equals("-g")){
                        try {
                            thisView.printGroup(thisModel.getGroup(Integer.parseInt(command[2])));
                        }catch (NumberFormatException e){
                            thisView.printError();
                        }
                    }
                    break;
                case "help":
                    thisView.printHelp();
                    break;
                case "exit":
                    exit="exit";
                    thisModel.saveXML("src\\xml\\test.xml");
                    break;
                default:
                    thisView.printError();
                    break;
            }
        }
    }


    public String[] parseCommand(String s) {
        String[] st = s.trim().split("\\s+");

        return st;
    }

}
