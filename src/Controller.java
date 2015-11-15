import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс <code>Controller</code> взаимодействует с классами <code>Model</code> и
 * <code>View</code>.
 * Класс обрабатывает все комманды пользователя и манипулирует данными.
 *
 * @author Karpenko Dmitry
 */
public class Controller {
    private Model thisModel;
    private View thisView;
    private Utility thisUtil = new Utility();

    /**
     * Конструктору необходимо явно указать с какой <code>Model</code> и
     * <code>View</code> ему работать.
     *
     * @param model Model
     *
     * @param view View
     */
    Controller(Model model, View view) {
        thisModel = model;
        thisView = view;
    }

    /**
     * Метод считывает данные, введенные в консоли и манипулирует ими.
     *
     * @throws IOException  Возникает при ошибке ввода\вывода.
     * @throws JAXBException Возникает при неверном указании источника данных или его отсутствии.
     * @throws ParseException Возникает при некорректном вводе формата даты.
     */
    public void run() throws IOException, JAXBException, ParseException {
        String command;
        boolean exit = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            thisUtil.loadZIP("src\\test.zip");
            thisModel = thisUtil.loadXML("src\\xml\\test.xml");
        } catch (UnmarshalException e){
            thisView.printError(View.Error.FILE_XML_NOT_FOUND);
            //TODO Поидее еще 1 эксепшен на возврате FileNotFound
        }

        while (!exit) {
            thisView.printConsole(View.Help.CONSOLE);
            command = reader.readLine();

            switch (command.trim().toLowerCase()){

                case "add":
                    boolean subExit = false;
                    while (!subExit){
                        thisView.printConsole(View.Help.ADD);
                        thisView.printConsole(View.Help.CONSOLE);
                        command = reader.readLine();
                        if("s".equals(command.trim().toLowerCase())){
                            String name;
                            String patronymic;
                            String surname;
                            String temp;
                            thisView.printConsole(View.Help.ADD_STUDENT_NAME);
                            thisView.printConsole(View.Help.CONSOLE);
                            temp = reader.readLine();
                            if(validate(temp)) {
                                name = temp;
                            } else {
                                thisView.printError(View.Error.FORBIDDEN_SYMBOLS);
                                continue;
                            }
                            thisView.printConsole(View.Help.ADD_STUDENT_PATR);
                            thisView.printConsole(View.Help.CONSOLE);
                            temp = reader.readLine();
                            if(validate(temp)) {
                               patronymic = temp;
                            } else {
                                thisView.printError(View.Error.FORBIDDEN_SYMBOLS);
                                continue;
                            }
                            thisView.printConsole(View.Help.ADD_STRUDENT_SURN);
                            thisView.printConsole(View.Help.CONSOLE);
                            temp = reader.readLine();
                            if(validate(temp)) {
                                surname = temp;
                            } else {
                                thisView.printError(View.Error.FORBIDDEN_SYMBOLS);
                                continue;
                            }
                            thisView.printConsole(View.Help.ADD_STUDENT_DATE);
                            thisView.printConsole(View.Help.CONSOLE);
                            Date date;
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                                date = format.parse(reader.readLine());
                            } catch (ParseException e){
                                thisView.printError(View.Error.STUDENT_ADD_DATE);
                                continue;
                            }
                            try {
                                thisModel.addStudent(name, surname, patronymic, date);
                                thisView.printConfirm(View.Confirm.STUDENT_ADD);
                            } catch (UnsupportedOperationException e){
                                thisView.printError(View.Error.STUDENT_ALREADY_DB);
                                continue;
                            }


                        } else if("g".equals(command.trim().toLowerCase())){
                            int number, tempInt;
                            String facult, tempString;
                            thisView.printConsole(View.Help.GROUP_NUMBER);
                            thisView.printConsole(View.Help.CONSOLE);
                            try {
                                tempInt = Integer.parseInt(reader.readLine());
                            } catch (NumberFormatException e) {
                                thisView.printError(View.Error.STUDENT_FORMAT);
                                continue;
                            }
                            if(tempInt >=0){
                                number = tempInt;
                            } else {
                                thisView.printError(View.Error.GROUP_FORMAT);
                                continue;
                            }
                            thisView.printConsole(View.Help.ADD_GROUP_FACULT);
                            thisView.printConsole(View.Help.CONSOLE);
                            tempString = reader.readLine();
                            if(validate(tempString)) {
                                facult = tempString;
                            } else {
                                thisView.printError(View.Error.FORBIDDEN_SYMBOLS);
                                continue;
                            }
                            try {
                                thisModel.addGroup(number,facult);
                                thisView.printConfirm(View.Confirm.GROUP_ADD);
                            } catch (UnsupportedOperationException e){
                                thisView.printError(View.Error.GROUP_ALREADY);
                                continue;
                            }


                        } else if("stg".equals(command.trim().toLowerCase())){
                            int studentID;
                            int groupID;
                            thisView.printConsole(View.Help.ID_STUDENT);
                            thisView.printConsole(View.Help.CONSOLE);
                            try {
                                studentID = Integer.parseInt(reader.readLine());
                            } catch (NumberFormatException e) {
                                thisView.printError(View.Error.STUDENT_FORMAT);
                                continue;
                            }
                            thisView.printConsole(View.Help.GROUP_NUMBER);
                            thisView.printConsole(View.Help.CONSOLE);
                            try {
                                groupID = Integer.parseInt(reader.readLine());
                            } catch (NumberFormatException e) {
                                thisView.printError(View.Error.STUDENT_FORMAT);
                                continue;
                            }
                            try {
                                thisModel.studentToGroup(studentID, groupID);
                                thisView.printConfirm(View.Confirm.STUDENT_IN_GROUP);
                            } catch (UnsupportedOperationException e){
                                thisView.printError(View.Error.STUDENT_ALREADY_GROUP);
                                continue;
                            }

                        } else if("exit".equals(command.toLowerCase())){
                            subExit = true;
                            thisView.printConfirm(View.Confirm.COMPLETE);

                        } else thisView.printError(View.Error.INVALID_SYNTAX_SECOND);
                    }
                    break;

                case "del":
                    subExit = false;
                    while (!subExit) {
                        thisView.printConsole(View.Help.DEL);
                        thisView.printConsole(View.Help.CONSOLE);
                        command = reader.readLine();
                        if("s".equals(command.trim().toLowerCase())){
                            thisView.printConsole(View.Help.ID_STUDENT);
                            thisView.printConsole(View.Help.CONSOLE);
                            try {
                                int studentID = Integer.parseInt(reader.readLine());
                                thisModel.delStudent(studentID);
                                thisView.printConfirm(View.Confirm.STUDENT_DELL);
                            } catch (UnsupportedOperationException e){
                                thisView.printError(View.Error.STUDENT_NOT_FOUND);
                                continue;
                            } catch (NumberFormatException e){
                                thisView.printError(View.Error.STUDENT_FORMAT);
                                continue;
                            }

                        } else if("g".equals(command.trim().toLowerCase())){
                            thisView.printConsole(View.Help.GROUP_NUMBER);
                            thisView.printConsole(View.Help.CONSOLE);
                            try {
                                int groupID = Integer.parseInt(reader.readLine());
                                thisModel.delGroup(groupID);
                                thisView.printConfirm(View.Confirm.GROUP_DELL);
                            } catch (UnsupportedOperationException e){
                                thisView.printError(View.Error.STUDENT_NOT_FOUND);
                                continue;
                            } catch (NumberFormatException e){
                                thisView.printError(View.Error.STUDENT_FORMAT);
                                continue;
                            }

                        } else if("exit".equals(command.trim().toLowerCase())){
                            subExit = true;
                            thisView.printConfirm(View.Confirm.COMPLETE);

                        } else thisView.printError(View.Error.INVALID_SYNTAX_SECOND);
                    }
                    break;

                case "mod":
                    subExit = false;
                    while (!subExit) {
                        thisView.printConsole(View.Help.MOD);
                        thisView.printConsole(View.Help.CONSOLE);
                        command = reader.readLine();
                        if ("s".equals(command.trim().toLowerCase())) {
                            int studentID;
                            String name;
                            String patronymic;
                            String surname;
                            String temp;
                            thisView.printConsole(View.Help.ID_STUDENT);
                            thisView.printConsole(View.Help.CONSOLE);
                            try {
                                studentID = Integer.parseInt(reader.readLine());
                            } catch (NumberFormatException e){
                                thisView.printError(View.Error.STUDENT_FORMAT);
                                continue;
                            }
                            thisView.printConsole(View.Help.ADD_STUDENT_NAME);
                            thisView.printConsole(View.Help.CONSOLE);
                            temp = reader.readLine();
                            if(validate(temp)) {
                                name = temp;
                            } else {
                                thisView.printError(View.Error.FORBIDDEN_SYMBOLS);
                                continue;
                            }
                            thisView.printConsole(View.Help.ADD_STUDENT_PATR);
                            thisView.printConsole(View.Help.CONSOLE);
                            temp = reader.readLine();
                            if(validate(temp)) {
                                patronymic = temp;
                            } else {
                                thisView.printError(View.Error.FORBIDDEN_SYMBOLS);
                                continue;
                            }
                            thisView.printConsole(View.Help.ADD_STRUDENT_SURN);
                            thisView.printConsole(View.Help.CONSOLE);
                            temp = reader.readLine();
                            if(validate(temp)) {
                                surname = temp;
                            } else {
                                thisView.printError(View.Error.FORBIDDEN_SYMBOLS);
                                continue;
                            }
                            thisView.printConsole(View.Help.ADD_STUDENT_DATE);
                            thisView.printConsole(View.Help.CONSOLE);
                            Date date;
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                                date = format.parse(reader.readLine());
                            } catch (ParseException e){
                                thisView.printError(View.Error.STUDENT_ADD_DATE);
                                continue;
                            }
                            try {
                                thisModel.modifyStudent(studentID, name, surname, patronymic, date);
                                thisView.printConfirm(View.Confirm.STUDENT_MOD);
                            } catch (UnsupportedOperationException e){
                                thisView.printError(View.Error.STUDENT_NOT_FOUND);
                                continue;
                            }

                        } else if("g".equals(command.trim().toLowerCase())){
                            int oldID;
                            int newID;
                            String facult, temp;
                            thisView.printConsole(View.Help.GROUP_NUMBER);
                            thisView.printConsole(View.Help.CONSOLE);
                            try {
                                oldID = Integer.parseInt(reader.readLine());
                            } catch (NumberFormatException e) {
                                thisView.printError(View.Error.STUDENT_FORMAT);
                                continue;
                            }
                            thisView.printConsole(View.Help.GROUP_NUMBER_NEW);
                            thisView.printConsole(View.Help.CONSOLE);
                            try {
                                newID = Integer.parseInt(reader.readLine());
                            } catch (NumberFormatException e){
                                thisView.printError(View.Error.STUDENT_FORMAT);
                                continue;
                            }
                            thisView.printConsole(View.Help.ADD_GROUP_FACULT);
                            thisView.printConsole(View.Help.CONSOLE);
                            temp = reader.readLine();
                            if(validate(temp)){
                                facult=temp;
                            } else {
                                thisView.printError(View.Error.FORBIDDEN_SYMBOLS);
                                continue;
                            }
                            try {
                                thisModel.modifyGroup(oldID, newID, facult);
                                thisView.printConfirm(View.Confirm.GROUP_MOD);
                            } catch (UnsupportedOperationException e){
                                thisView.printError(View.Error.GROUP_NOT_FOUND);
                                continue;
                            }

                        } else if("exit".equals(command.trim().toLowerCase())){
                            subExit = true;
                            thisView.printConfirm(View.Confirm.COMPLETE);

                        } else thisView.printError(View.Error.INVALID_SYNTAX_SECOND);
                    }
                    break;

                case "view":
                    subExit = false;
                    while (!subExit) {
                        thisView.printConsole(View.Help.VIEW);
                        thisView.printConsole(View.Help.CONSOLE);
                        command = reader.readLine();
                        if ("alls".equals(command.trim().toLowerCase())) {
                            for(Student student: thisModel.getStudents()){
                                int id = student.getId();
                                String name = student.getName();
                                String patronymic = student.getPatronymic();
                                String surname = student.getSurname();
                                Date date = student.getDate();
                                thisView.printStudent(id, name, patronymic, surname, date);
                            }
                            thisView.printConfirm(View.Confirm.COMPLETE);

                        } else if ("allg".equals(command.trim().toLowerCase())) {
                            for(Group group: thisModel.getGroups()){
                                int number = group.getNumber();
                                String facult = group.getFacult();
                                thisView.printGroup(number, facult, false);
                            }
                            thisView.printConfirm(View.Confirm.COMPLETE);

                        } else if ("s".equals(command.trim().toLowerCase())) {
                            int studentID;
                            thisView.printConsole(View.Help.ID_STUDENT);
                            thisView.printConsole(View.Help.CONSOLE);
                            try {
                                studentID = Integer.parseInt(reader.readLine())-1;
                            } catch (NumberFormatException e){
                                thisView.printError(View.Error.STUDENT_FORMAT);
                                continue;
                            }
                            Student student;
                            try{
                                student = thisModel.getStudents().get(studentID);
                            } catch (IndexOutOfBoundsException e){
                                thisView.printError(View.Error.STUDENT_NOT_FOUND);
                                continue;
                            }
                            int id = student.getId();
                            String name = student.getName();
                            String patronymic = student.getPatronymic();
                            String surname = student.getSurname();
                            Date date = student.getDate();
                            thisView.printStudent(id, name, patronymic, surname, date);
                            thisView.printConfirm(View.Confirm.COMPLETE);

                        } else if ("g".equals(command.trim().toLowerCase())) {
                            int id;
                            thisView.printConsole(View.Help.GROUP_NUMBER);
                            thisView.printConsole(View.Help.CONSOLE);
                            try {
                                id = Integer.parseInt(reader.readLine());
                            } catch (NumberFormatException e){
                                thisView.printError(View.Error.STUDENT_FORMAT);
                                continue;
                            }
                            Group group;
                            try {
                                group = thisModel.getGroup(id);
                            } catch (IndexOutOfBoundsException e){
                                thisView.printError(View.Error.GROUP_NOT_FOUND);
                                continue;
                            }
                            int number = group.getNumber();
                            String facult = group.getFacult();
                            thisView.printGroup(number, facult, true);
                            for(Student student: group.getStudents()){
                                int idStudent = student.getId();
                                String name = student.getName();
                                String patronymic = student.getPatronymic();
                                String surname = student.getSurname();
                                Date date = student.getDate();
                                thisView.printStudent(idStudent, name, patronymic, surname, date);
                            }
                            thisView.printConfirm(View.Confirm.COMPLETE);

                        } else if("exit".equals(command.trim().toLowerCase())){
                            subExit = true;
                            thisView.printConfirm(View.Confirm.COMPLETE);

                        } else thisView.printError(View.Error.INVALID_SYNTAX_SECOND);
                    }
                    break;

                case "find":
                    subExit = false;
                    while (!subExit) {
                        thisView.printConsole(View.Help.FIND);
                        thisView.printConsole(View.Help.CONSOLE);
                        command = reader.readLine();
                        if ("s".equals(command.trim().toLowerCase())) {
                            thisView.printConsole(View.Help.FIND_STRING);
                            thisView.printConsole(View.Help.CONSOLE);
                            command = reader.readLine();
                            if(!validateFind(command)){
                                thisView.printError(View.Error.FORBIDDEN_SYMBOLS);
                                continue;
                            }
                            ArrayList<Student> students;
                            students = thisModel.searchStudent(command);
                            if(students.isEmpty()){
                                thisView.printError(View.Error.STUDENT_NOT_FOUND);
                                continue;
                            }else {
                                for (Student student : students) {
                                    int id = student.getId();
                                    String name = student.getName();
                                    String patronymic = student.getPatronymic();
                                    String surname = student.getSurname();
                                    Date date = student.getDate();
                                    thisView.printStudent(id, name, patronymic, surname, date);
                                }
                                thisView.printConfirm(View.Confirm.COMPLETE);
                            }

                        } else if ("g".equals(command.trim().toLowerCase())) {
                            thisView.printConsole(View.Help.FIND_STRING);
                            thisView.printConsole(View.Help.CONSOLE);
                            command = reader.readLine();
                            if(!validateFind(command)){
                                thisView.printError(View.Error.FORBIDDEN_SYMBOLS);
                                continue;
                            }
                            ArrayList<Group> groups;
                            groups = thisModel.searchGroup(command);
                            if(groups.isEmpty()){
                                thisView.printError(View.Error.GROUP_NOT_FOUND);
                                continue;
                            }else {
                                for (int i = 0; i < groups.size(); i++) {
                                    int number = groups.get(i).getNumber();
                                    String facult = groups.get(i).getFacult();
                                    thisView.printGroup(number, facult, false);
                                }
                                thisView.printConfirm(View.Confirm.COMPLETE);
                            }
                        } else if("exit".equals(command.trim().toLowerCase())){
                            subExit = true;
                            thisView.printConfirm(View.Confirm.COMPLETE);

                        } else thisView.printError(View.Error.INVALID_SYNTAX_SECOND);
                    }
                    break;

                case "file":
                    thisView.printConsole(View.Help.PATH);
                    thisView.printConsole(View.Help.CONSOLE);
                    command = reader.readLine();
                    try {
                        thisModel = thisUtil.fileToFile(command, thisModel);
                        thisView.printConfirm(View.Confirm.MERGER);
                    } catch (UnmarshalException e) {
                        thisView.printError(View.Error.FILE_NOT_FOUND);
                    }
                    break;

                case "help":
                    thisView.printHelp();
                    break;

                case "exit":
                    exit = true;
                    thisUtil.saveXML("src\\xml\\test.xml", thisModel);
                    thisUtil.saveZIP("src\\xml\\test.xml");
                    break;
                default:
                    thisView.printError(View.Error.HELP);
                    break;
            }



        }

    }

    /**
     * Метод проводит валидацию на входную строку для дальнейшего поиска по ней.
     *
     * @param string Входная строка для проверки.
     *
     * @return true - если строка содержит только буквы, цифры, знак[?] или знак[*].
     *         false - если строка содержит другие символы.
     */
    public static boolean validateFind(String string) {
        Pattern pattern = Pattern.compile("^[а-яА-ЯёЁa-zA-Z0-9-\\s?*]+$");
            Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * Метод проводит валидацию на входную строку.
     *
     * @param string Входная строка для проверки.
     *
     * @return true - если строка содержит только буквы и цифры.
     *         false - если строка содержил другие символы.
     */
    public static boolean validate(String string) {
        if(string.trim().isEmpty()) return false;
        Pattern pattern = Pattern.compile("^[а-яА-ЯёЁa-zA-Z0-9-\\s]+$");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
