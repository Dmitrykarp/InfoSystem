import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;


public class Controller {
    private Model thisModel;
    private View thisView;


    Controller(Model model, View view) {
        thisModel = model;
        thisView = view;
    }

    public void run() throws IOException, JAXBException {
        String[] command;
        boolean exit = true;

        thisModel.loadZIP("src\\test.zip");
        thisModel = thisModel.loadXML("src\\xml\\test.xml", thisModel);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (exit) {
            thisView.printConsole();

            command = parseCommand(reader.readLine());

            switch (command[0].toLowerCase()) {
                case "add":
                    try {
                        if ("-s".equals(command[1].toLowerCase())) {
                            try {
                                Date d = new Date();
                                String[] st = command[5].split("\\.");
                                d.setDate(Integer.parseInt(st[0]));
                                d.setMonth(Integer.parseInt(st[1]) - 1);
                                d.setYear(Integer.parseInt(st[2]) - 1900);
                                thisModel.addStudent(command[2], command[4], command[3], d);
                                thisView.printConfirm(View.Confirm.STUDENT_ADD);
                            } catch (IndexOutOfBoundsException e){
                                thisView.printError(View.Error.INVALID_SYNTAX);
                            }catch (NumberFormatException e){
                                thisView.printError(View.Error.STUDENT_ADD_DATE);
                            }catch (RuntimeException e){
                                thisView.printError(View.Error.STUDENT_ALREADY_DB);
                            }
                        } else if ("-g".equals(command[1].toLowerCase())) {
                            try {
                                thisModel.addGroup(Integer.parseInt(command[2]), command[3]);
                                thisView.printConfirm(View.Confirm.GROUP_ADD);
                            } catch (NumberFormatException e) {
                                thisView.printError(View.Error.HELP);
                            } catch (RuntimeException e){
                                thisView.printError(View.Error.GROUP_ALREADY);
                            }
                        } else if ("-stg".equals(command[1].toLowerCase())) {
                            try {
                                thisModel.studentToGroup(Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                                thisView.printConfirm(View.Confirm.STUDENT_IN_GROUP);
                            } catch (NumberFormatException e) {
                                thisView.printError(View.Error.HELP);
                            } catch (RuntimeException e){
                                thisView.printError(View.Error.STUDENT_ALREADY_GROUP);
                            }
                        } else thisView.printError(View.Error.INVALID_SYNTAX);

                    } catch (ArrayIndexOutOfBoundsException e) {
                        thisView.printError(View.Error.INVALID_SYNTAX);
                    }
                    break;
                case "del":
                    try{
                        if("-s".equals(command[1].toLowerCase())){
                            thisModel.delStudent(Integer.parseInt(command[2]));
                            thisView.printConfirm(View.Confirm.STUDENT_DELL);
                        } else if("-g".equals(command[1].toLowerCase())){
                            thisModel.delGroup(Integer.parseInt(command[2]));
                            thisView.printConfirm(View.Confirm.GROUP_DELL);
                        } else thisView.printError(View.Error.INVALID_SYNTAX);

                    }catch (NumberFormatException e){
                        thisView.printError(View.Error.INVALID_SYNTAX);
                    }catch (ArrayIndexOutOfBoundsException e){
                        thisView.printError(View.Error.INVALID_SYNTAX);
                    }catch (RuntimeException e) {
                        if("-s".equals(command[1].toLowerCase())) thisView.printError(View.Error.STUDENT_NOT_FOUND);
                        else if("-g".equals(command[1].toLowerCase())) thisView.printError(View.Error.GROUP_NOT_FOUND);
                        else thisView.printError(View.Error.UNKNOWN_ERROR);
                    }
                    break;
                case "mod":
                    break;
                case "view":
                    try {
                        if ("-alls".equals(command[1].toLowerCase())) {
                            for (int i = 0; i < thisModel.getStudents().size(); i++) {
                                int id = thisModel.getStudents().get(i).getId();
                                String na = thisModel.getStudents().get(i).getName();
                                String pa = thisModel.getStudents().get(i).getPatronymic();
                                String su = thisModel.getStudents().get(i).getSurname();
                                Date da = thisModel.getStudents().get(i).getDate();
                                thisView.printStudent(id, na, pa, su, da);
                            }

                        } else if ("-allg".equals(command[1].toLowerCase())) {
                            for (int i = 0; i < thisModel.getGroups().size(); i++) {
                                int num = thisModel.getGroups().get(i).getNumber();
                                String fac = thisModel.getGroups().get(i).getFacult();
                                thisView.printGroup(num, fac, true);
                            }
                        } else if ("-s".equals(command[1].toLowerCase())) {
                            try {
                                int i = Integer.parseInt(command[2]) - 1;
                                int id = thisModel.getStudents().get(i).getId();
                                String na = thisModel.getStudents().get(i).getName();
                                String pa = thisModel.getStudents().get(i).getPatronymic();
                                String su = thisModel.getStudents().get(i).getSurname();
                                Date da = thisModel.getStudents().get(i).getDate();
                                thisView.printStudent(id, na, pa, su, da);

                            } catch (NumberFormatException e) {
                                thisView.printError(View.Error.HELP);
                            } catch (IndexOutOfBoundsException e) {
                                thisView.printError(View.Error.STUDENT_NOT_FOUND);
                            }
                        } else if ("-g".equals(command[1].toLowerCase())) {
                            try {
                                int id = Integer.parseInt(command[2]);
                                int num = thisModel.getGroup(id).getNumber();
                                String fac = thisModel.getGroup(id).getFacult();
                                thisView.printGroup(num, fac, false);
                                for (int i = 0; i < thisModel.getGroup(id).getStudents().size(); i++) {
                                    int ids = thisModel.getGroup(id).getStudents().get(i).getId();
                                    String na = thisModel.getGroup(id).getStudents().get(i).getName();
                                    String pa = thisModel.getGroup(id).getStudents().get(i).getPatronymic();
                                    String su = thisModel.getGroup(id).getStudents().get(i).getSurname();
                                    Date da = thisModel.getGroup(id).getStudents().get(i).getDate();
                                    thisView.printStudent(ids, na, pa, su, da);
                                }

                            } catch (NumberFormatException e) {
                                thisView.printError(View.Error.HELP);
                            } catch (RuntimeException e) {
                                thisView.printError(View.Error.GROUP_NOT_FOUND);
                            }
                        } else thisView.printError(View.Error.INVALID_SYNTAX);

                    } catch (ArrayIndexOutOfBoundsException e) {
                        thisView.printError(View.Error.INVALID_SYNTAX);
                    }
                    break;
                case "help":
                    thisView.printHelp();
                    break;
                case "exit":
                    exit = false;
                    thisModel.saveXML("src\\xml\\test.xml");
                    thisModel.saveZIP("src\\xml\\test.xml");
                    break;
                default:
                    thisView.printError(View.Error.HELP);
                    break;
            }
        }
    }


    public String[] parseCommand(String s) {
        String[] st = s.trim().split("\\s+");

        return st;
    }

}
