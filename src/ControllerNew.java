import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
                    subExit = false;
                    while (!subExit) {
                        thisView.printConsole(View.Help.VIEW);
                        thisView.printConsole(View.Help.CONSOLE);
                        command = reader.readLine();
                        if ("alls".equals(command.toLowerCase())) {
                            for (int i = 0; i < thisModel.getStudents().size(); i++) {
                                int id = thisModel.getStudents().get(i).getId();
                                String name = thisModel.getStudents().get(i).getName();
                                String patronymic = thisModel.getStudents().get(i).getPatronymic();
                                String surname = thisModel.getStudents().get(i).getSurname();
                                Date date = thisModel.getStudents().get(i).getDate();
                                thisView.printStudent(id, name, patronymic, surname, date);
                            }

                        } else if ("allg".equals(command.toLowerCase())) {
                            for (int i = 0; i < thisModel.getGroups().size(); i++) {
                                int number = thisModel.getGroups().get(i).getNumber();
                                String facult = thisModel.getGroups().get(i).getFacult();
                                thisView.printGroup(number, facult, true);
                            }

                        } else if ("s".equals(command.toLowerCase())) {
                            thisView.printConsole(View.Help.ID_STUDENT);
                            thisView.printConsole(View.Help.CONSOLE);
                            int studentID = Integer.parseInt(reader.readLine())-1;
                            int id = thisModel.getStudents().get(studentID).getId();
                            String name = thisModel.getStudents().get(studentID).getName();
                            String patronymic = thisModel.getStudents().get(studentID).getPatronymic();
                            String surname = thisModel.getStudents().get(studentID).getSurname();
                            Date date = thisModel.getStudents().get(studentID).getDate();
                            thisView.printStudent(id, name, patronymic, surname, date);

                        } else if ("g".equals(command.toLowerCase())) {
                            thisView.printConsole(View.Help.GROUP_NUMBER);
                            thisView.printConsole(View.Help.CONSOLE);
                            int id = Integer.parseInt(reader.readLine());
                            int number = thisModel.getGroup(id).getNumber();
                            String facult = thisModel.getGroup(id).getFacult();
                            thisView.printGroup(number, facult, false);
                            for (int i = 0; i < thisModel.getGroup(id).getStudents().size(); i++) {
                                int idStudent = thisModel.getGroup(id).getStudents().get(i).getId();
                                String name = thisModel.getGroup(id).getStudents().get(i).getName();
                                String patronymic = thisModel.getGroup(id).getStudents().get(i).getPatronymic();
                                String surname = thisModel.getGroup(id).getStudents().get(i).getSurname();
                                Date date = thisModel.getGroup(id).getStudents().get(i).getDate();
                                thisView.printStudent(idStudent, name, patronymic, surname, date);
                            }

                        } else if("exit".equals(command.toLowerCase())){
                            subExit = true;

                        } else thisView.printError(View.Error.INVALID_SYNTAX);
                    }
                    break;

                case "find":
                    subExit = false;
                    while (!subExit) {
                        thisView.printConsole(View.Help.FIND);
                        thisView.printConsole(View.Help.CONSOLE);
                        command = reader.readLine();
                        if ("s".equals(command.toLowerCase())) {
                            thisView.printConsole(View.Help.FIND_STRING);
                            thisView.printConsole(View.Help.CONSOLE);
                            command = reader.readLine();
                            ArrayList<Student> students;
                            students = thisModel.searchStudent(command);
                            for (int i = 0; i < students.size(); i++) {
                                int id = students.get(i).getId();
                                String name = students.get(i).getName();
                                String patronymic = students.get(i).getPatronymic();
                                String surname = students.get(i).getSurname();
                                Date date = students.get(i).getDate();
                                thisView.printStudent(id, name, patronymic, surname, date);
                            }

                        } else if ("g".equals(command.toLowerCase())) {
                            thisView.printConsole(View.Help.FIND_STRING);
                            thisView.printConsole(View.Help.CONSOLE);
                            command = reader.readLine();
                            ArrayList<Group> groups;
                            groups = thisModel.searchGroup(command);
                            for (int i = 0; i < groups.size(); i++) {
                                int number = groups.get(i).getNumber();
                                String facult = groups.get(i).getFacult();
                                thisView.printGroup(number, facult, true);
                            }

                        } else thisView.printError(View.Error.INVALID_SYNTAX);
                    }
                    break;

                case "file":
                    thisView.printConsole(View.Help.PATH);
                    thisView.printConsole(View.Help.CONSOLE);
                    command = reader.readLine();
                    thisModel = thisUtil.fileToFile(command,thisModel);
                    break;

                case "help":
                    thisView.printHelp();
                    break;

                case "exit":
                    exit = false;
                    thisUtil.saveXML("src\\xml\\test.xml", thisModel);
                    thisUtil.saveZIP("src\\xml\\test.xml");
                    break;
                default:
                    thisView.printError(View.Error.HELP);
                    break;
            }



        }

    }

    public static boolean validate(String s) {
        Pattern pattern = Pattern.compile("");
        //TODO неуверен
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
