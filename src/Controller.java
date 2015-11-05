import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        thisModel.loadZIP("src\\test.zip");
        thisModel = thisModel.loadXML("src\\xml\\test.xml", thisModel);

        while (exit) {
            thisView.printConsole();
            command = parseCommand(reader.readLine());

            switch (command[0].toLowerCase()) {
                case "add":
                    try {
                        if ("-s".equals(command[1].toLowerCase())) {
                            try {
                                Date date = new Date();
                                String[] split = command[5].split("\\.");
                                date.setDate(Integer.parseInt(split[0]));
                                date.setMonth(Integer.parseInt(split[1]) - 1);
                                date.setYear(Integer.parseInt(split[2]) - 1900);
                                thisModel.addStudent(command[2], command[4], command[3], date);
                                thisView.printConfirm(View.Confirm.STUDENT_ADD);

                            } catch (IndexOutOfBoundsException e) {
                                thisView.printError(View.Error.INVALID_SYNTAX);
                            } catch (NumberFormatException e) {
                                thisView.printError(View.Error.STUDENT_ADD_DATE);
                            } catch (RuntimeException e) {
                                thisView.printError(View.Error.STUDENT_ALREADY_DB);
                            }

                        } else if ("-g".equals(command[1].toLowerCase())) {
                            try {
                                thisModel.addGroup(Integer.parseInt(command[2]), command[3]);
                                thisView.printConfirm(View.Confirm.GROUP_ADD);
                            } catch (NumberFormatException e) {
                                thisView.printError(View.Error.HELP);
                            } catch (RuntimeException e) {
                                thisView.printError(View.Error.GROUP_ALREADY);
                            }

                        } else if ("-stg".equals(command[1].toLowerCase())) {
                            try {
                                thisModel.studentToGroup(Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                                thisView.printConfirm(View.Confirm.STUDENT_IN_GROUP);
                            } catch (NumberFormatException e) {
                                thisView.printError(View.Error.HELP);
                            } catch (RuntimeException e) {
                                thisView.printError(View.Error.STUDENT_ALREADY_GROUP);
                            }

                        } else thisView.printError(View.Error.INVALID_SYNTAX);

                    } catch (ArrayIndexOutOfBoundsException e) {
                        thisView.printError(View.Error.INVALID_SYNTAX);
                    }
                    break;

                case "del":
                    try {
                        if ("-s".equals(command[1].toLowerCase())) {
                            thisModel.delStudent(Integer.parseInt(command[2]));
                            thisView.printConfirm(View.Confirm.STUDENT_DELL);

                        } else if ("-g".equals(command[1].toLowerCase())) {
                            thisModel.delGroup(Integer.parseInt(command[2]));
                            thisView.printConfirm(View.Confirm.GROUP_DELL);

                        } else thisView.printError(View.Error.INVALID_SYNTAX);

                    } catch (NumberFormatException e) {
                        thisView.printError(View.Error.INVALID_SYNTAX);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        thisView.printError(View.Error.INVALID_SYNTAX);
                    } catch (RuntimeException e) {
                        if ("-s".equals(command[1].toLowerCase())) thisView.printError(View.Error.STUDENT_NOT_FOUND);
                        else if ("-g".equals(command[1].toLowerCase())) thisView.printError(View.Error.GROUP_NOT_FOUND);
                        else thisView.printError(View.Error.UNKNOWN_ERROR);
                    }
                    break;

                case "mod":
                    try {
                        if ("-s".equals(command[1].toLowerCase())) {
                            int id = Integer.parseInt(command[2]);
                            String name = command[3];
                            String patronymic = command[4];
                            String surname = command[5];
                            Date date = new Date();
                            String[] st = command[6].split("\\.");
                            date.setDate(Integer.parseInt(st[0]));
                            date.setMonth(Integer.parseInt(st[1]) - 1);
                            date.setYear(Integer.parseInt(st[2]) - 1900);
                            thisModel.modifyStudent(id, name, surname, patronymic, date);
                            thisView.printConfirm(View.Confirm.STUDENT_MOD);

                        } else if ("-g".equals(command[1].toLowerCase())) {
                            int idOld = Integer.parseInt(command[2]);
                            int idNew = Integer.parseInt(command[3]);
                            String facult = command[4];
                            thisModel.modifyGroup(idOld, idNew, facult);
                            thisView.printConfirm(View.Confirm.GROUP_MOD);

                        } else thisView.printError(View.Error.INVALID_SYNTAX);

                    } catch (ArrayIndexOutOfBoundsException e) {
                        thisView.printError(View.Error.INVALID_SYNTAX);
                    } catch (NumberFormatException e) {
                        thisView.printError(View.Error.INVALID_SYNTAX);
                    } catch (RuntimeException e) {
                        if ("-s".equals(command[1].toLowerCase())) thisView.printError(View.Error.STUDENT_NOT_FOUND);
                        else if ("-g".equals(command[1].toLowerCase())) thisView.printError(View.Error.GROUP_NOT_FOUND);
                        else thisView.printError(View.Error.UNKNOWN_ERROR);
                    }
                    break;

                case "view":
                    try {
                        if ("-alls".equals(command[1].toLowerCase())) {
                            for (int i = 0; i < thisModel.getStudents().size(); i++) {
                                int id = thisModel.getStudents().get(i).getId();
                                String name = thisModel.getStudents().get(i).getName();
                                String patronymic = thisModel.getStudents().get(i).getPatronymic();
                                String surname = thisModel.getStudents().get(i).getSurname();
                                Date date = thisModel.getStudents().get(i).getDate();
                                thisView.printStudent(id, name, patronymic, surname, date);
                            }

                        } else if ("-allg".equals(command[1].toLowerCase())) {
                            for (int i = 0; i < thisModel.getGroups().size(); i++) {
                                int number = thisModel.getGroups().get(i).getNumber();
                                String facult = thisModel.getGroups().get(i).getFacult();
                                thisView.printGroup(number, facult, true);
                            }

                        } else if ("-s".equals(command[1].toLowerCase())) {
                            try {
                                int idStudent = Integer.parseInt(command[2]) - 1;
                                int id = thisModel.getStudents().get(idStudent).getId();
                                String name = thisModel.getStudents().get(idStudent).getName();
                                String patronymic = thisModel.getStudents().get(idStudent).getPatronymic();
                                String surname = thisModel.getStudents().get(idStudent).getSurname();
                                Date date = thisModel.getStudents().get(idStudent).getDate();
                                thisView.printStudent(id, name, patronymic, surname, date);

                            } catch (NumberFormatException e) {
                                thisView.printError(View.Error.HELP);
                            } catch (IndexOutOfBoundsException e) {
                                thisView.printError(View.Error.STUDENT_NOT_FOUND);
                            }

                        } else if ("-g".equals(command[1].toLowerCase())) {
                            try {
                                int id = Integer.parseInt(command[2]);
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

                case "find":
                    try {
                        if ("-s".equals(command[1].toLowerCase())) {
                            ArrayList<Student> students;
                            students = thisModel.searchStudent(command[2]);
                            for (int i = 0; i < students.size(); i++) {
                                int id = students.get(i).getId();
                                String name = students.get(i).getName();
                                String patronymic = students.get(i).getPatronymic();
                                String surname = students.get(i).getSurname();
                                Date date = students.get(i).getDate();
                                thisView.printStudent(id, name, patronymic, surname, date);
                            }

                        } else if ("-g".equals(command[1].toLowerCase())) {
                            ArrayList<Group> groups = new ArrayList<Group>();
                            groups = thisModel.searchGroup(command[2]);
                            for (int i = 0; i < groups.size(); i++) {
                                int number = groups.get(i).getNumber();
                                String facult = groups.get(i).getFacult();
                                thisView.printGroup(number, facult, true);
                            }

                        } else thisView.printError(View.Error.INVALID_SYNTAX);

                    } catch (ArrayIndexOutOfBoundsException e) {
                        thisView.printError(View.Error.INVALID_SYNTAX);
                    } catch (RuntimeException e) {
                        if ("-s".equals(command[1].toLowerCase())) thisView.printError(View.Error.STUDENT_NOT_FOUND);
                        else if ("-g".equals(command[1].toLowerCase())) thisView.printError(View.Error.GROUP_NOT_FOUND);
                        else thisView.printError(View.Error.UNKNOWN_ERROR);
                    }
                    break;

                case "file":
                    try {
                        thisModel = thisModel.fileToFile(command[1].toLowerCase(), thisModel);
                        thisView.printConfirm(View.Confirm.MERGER);

                    } catch (ArrayIndexOutOfBoundsException e) {
                        thisView.printError(View.Error.INVALID_SYNTAX);
                    } catch (UnmarshalException e) {
                        thisView.printError(View.Error.FILE_NOT_FOUND);
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

    public String[] parseCommand(String command) {
        String[] splitCommand = command.trim().split("\\s+");
        return splitCommand;
    }
}
