import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerNew {
    private Model thisModel;
    private View thisView;
    private Utility thisUtil;

    ControllerNew(Model model, View view) {
        thisModel = model;
        thisView = view;
    }

    public void run() throws IOException, JAXBException, ParseException {
        String command;
        boolean exit = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        /*try {
            thisUtil.loadZIP("src\\test.zip");
            thisModel = thisUtil.loadXML("src\\xml\\test.xml");
        } catch (UnmarshalException e){
            thisView.printError(View.Error.FILE_XML_NOT_FOUND);
            //TODO Поидее еще 1 эксепшен на возврате FileNotFound
        }*/

        while (!exit) {
            thisView.printConsole(View.Help.CONSOLE);
            command = reader.readLine();

            switch (command.toLowerCase()){

                case "add":
                    boolean subExit = false;
                    while (!subExit){
                        thisView.printConsole(View.Help.ADD);
                        thisView.printConsole(View.Help.CONSOLE);
                        command = reader.readLine();
                        if("s".equals(command.toLowerCase())){
                            //TODO валидацию сделать
                            thisView.printConsole(View.Help.ADD_STUDENT_NAME);
                            thisView.printConsole(View.Help.CONSOLE);
                            String name = reader.readLine();
                            thisView.printConsole(View.Help.ADD_STUDENT_PATR);
                            thisView.printConsole(View.Help.CONSOLE);
                            String patronymic = reader.readLine();
                            thisView.printConsole(View.Help.ADD_STRUDENT_SURN);
                            thisView.printConsole(View.Help.CONSOLE);
                            String surname = reader.readLine();
                            thisView.printConsole(View.Help.ADD_STUDENT_DATE);
                            thisView.printConsole(View.Help.CONSOLE);
                            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                            Date date = format.parse(reader.readLine());
                            thisModel.addStudent(name, surname, patronymic, date);
                            thisView.printConfirm(View.Confirm.STUDENT_ADD);

                        } else if("g".equals(command.toLowerCase())){
                            thisView.printConsole(View.Help.GROUP_NUMBER);
                            thisView.printConsole(View.Help.CONSOLE);
                            int number = Integer.parseInt(reader.readLine());
                            thisView.printConsole(View.Help.ADD_GROUP_FACULT);
                            thisView.printConsole(View.Help.CONSOLE);
                            String facult = reader.readLine();
                            thisModel.addGroup(number,facult);
                            thisView.printConfirm(View.Confirm.GROUP_ADD);


                        } else if("stg".equals(command.toLowerCase())){
                            thisView.printConsole(View.Help.ID_STUDENT);
                            thisView.printConsole(View.Help.CONSOLE);
                            int studentID = Integer.parseInt(reader.readLine());
                            thisView.printConsole(View.Help.ADD_STG_ID_GROUP);
                            thisView.printConsole(View.Help.CONSOLE);
                            int groupID = Integer.parseInt(reader.readLine());
                            thisModel.studentToGroup(studentID, groupID);
                            thisView.printConfirm(View.Confirm.STUDENT_IN_GROUP);

                        } else if("exit".equals(command.toLowerCase())){
                            subExit = true;
                        } else thisView.printError(View.Error.INVALID_SYNTAX);
                    }
                    break;

                case "del":
                    subExit = false;
                    while (!subExit) {
                        thisView.printConsole(View.Help.DEL);
                        thisView.printConsole(View.Help.CONSOLE);
                        command = reader.readLine();
                        if("s".equals(command.toLowerCase())){
                            thisView.printConsole(View.Help.ID_STUDENT);
                            thisView.printConsole(View.Help.CONSOLE);
                            int studentID = Integer.parseInt(reader.readLine());
                            thisModel.delStudent(studentID);
                            thisView.printConfirm(View.Confirm.STUDENT_DELL);

                        } else if("g".equals(command.toLowerCase())){
                            thisView.printConsole(View.Help.GROUP_NUMBER);
                            thisView.printConsole(View.Help.CONSOLE);
                            int groupID = Integer.parseInt(reader.readLine());
                            thisModel.delGroup(groupID);
                            thisView.printConfirm(View.Confirm.GROUP_DELL);

                        } else if("exit".equals(command.toLowerCase())){
                            subExit = true;

                        } else thisView.printError(View.Error.INVALID_SYNTAX);
                    }
                    break;

                case "mod":
                    subExit = false;
                    while (!subExit) {
                        thisView.printConsole(View.Help.MOD);
                        thisView.printConsole(View.Help.CONSOLE);
                        command = reader.readLine();
                        if ("s".equals(command.toLowerCase())) {
                            thisView.printConsole(View.Help.ID_STUDENT);
                            thisView.printConsole(View.Help.CONSOLE);
                            int studentID = Integer.parseInt(reader.readLine());
                            thisView.printConsole(View.Help.ADD_STUDENT_NAME);
                            thisView.printConsole(View.Help.CONSOLE);
                            String name = reader.readLine();
                            thisView.printConsole(View.Help.ADD_STUDENT_PATR);
                            thisView.printConsole(View.Help.CONSOLE);
                            String patronymic = reader.readLine();
                            thisView.printConsole(View.Help.ADD_STRUDENT_SURN);
                            thisView.printConsole(View.Help.CONSOLE);
                            String surname = reader.readLine();
                            thisView.printConsole(View.Help.ADD_STUDENT_DATE);
                            thisView.printConsole(View.Help.CONSOLE);
                            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                            Date date = format.parse(reader.readLine());
                            thisModel.modifyStudent(studentID, name, surname, patronymic, date);
                            thisView.printConfirm(View.Confirm.STUDENT_MOD);

                        } else if("g".equals(command.toLowerCase())){
                            thisView.printConsole(View.Help.GROUP_NUMBER);
                            thisView.printConsole(View.Help.CONSOLE);
                            int oldID = Integer.parseInt(reader.readLine());
                            thisView.printConsole(View.Help.GROUP_NUMBER_NEW);
                            thisView.printConsole(View.Help.CONSOLE);
                            int newID = Integer.parseInt(reader.readLine());
                            thisView.printConsole(View.Help.ADD_GROUP_FACULT);
                            thisView.printConsole(View.Help.CONSOLE);
                            String facult = reader.readLine();
                            thisModel.modifyGroup(oldID, newID, facult);
                            thisView.printConfirm(View.Confirm.GROUP_MOD);

                        } else if("exit".equals(command.toLowerCase())){
                            subExit = true;

                        } else thisView.printError(View.Error.INVALID_SYNTAX);
                    }
                    break;

                case "view":
                    
                    break;
                case "find":
                    break;
                case "file":
                    break;
                case "help":
                    break;
                case "exit":
                    break;
                default:
                    thisView.printError(View.Error.HELP);
                    break;
            }



        }

    }

    public String[] parseCommand(String command) {
        String[] splitCommand = command.trim().split("\\s+");
        return splitCommand;
    }

    public static boolean validate(String s) {
        Pattern pattern = Pattern.compile("");
        //TODO неуверен
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
