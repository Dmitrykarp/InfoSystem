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


    public void run() throws IOException, JAXBException {
        String[] command;
        String exit = " ";
        Boolean confirm;

        thisModel = thisModel.loadXML("src\\xml\\test.xml", thisModel);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (!exit.equals("exit")) {
            thisView.printConsole();

            command = parseCommand(reader.readLine());

            switch (command[0].toLowerCase()) {
                case "add":
                    try {
                        if (command[1].toLowerCase().equals("-s")) {
                            confirm = thisModel.addStudent(command[2], command[4], command[3], command[5]);
                            thisView.printConfirm(confirm, command[1]);
                        } else if (command[1].toLowerCase().equals("-g")) {
                            try {
                                confirm = thisModel.addGroup(Integer.parseInt(command[2]), command[3]);
                                thisView.printConfirm(confirm, command[1]);
                            } catch (NumberFormatException e) {
                                thisView.printError(0);
                            }
                        } else if (command[1].toLowerCase().equals("-stg")) {
                            try {
                                confirm = thisModel.studentToGroup(Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                                thisView.printConfirm(confirm, command[1]);
                            } catch (NumberFormatException e) {
                                thisView.printError(0);
                            }
                        } else thisView.printError(0);

                    } catch (ArrayIndexOutOfBoundsException e) {
                        thisView.printError(3);
                    }
                    break;
                case "del":
                    break;
                case "mod":
                    break;
                case "view":
                    try {
                        if (command[1].toLowerCase().equals("-alls")) {
                            for (int i = 0; i < thisModel.getStudent().size(); i++) {
                                int id = thisModel.getStudent().get(i).getId();
                                String na = thisModel.getStudent().get(i).getName();
                                String pa = thisModel.getStudent().get(i).getPatronymic();
                                String su = thisModel.getStudent().get(i).getSurname();
                                String da = thisModel.getStudent().get(i).getDate();
                                thisView.printStudent(id, na, pa, su, da);
                            }

                        } else if (command[1].toLowerCase().equals("-allg")) {
                            for (int i = 0; i < thisModel.getGroup().size(); i++) {
                                int num = thisModel.getGroup().get(i).getNumber();
                                String fac = thisModel.getGroup().get(i).getFacult();
                                thisView.printGroup(num, fac, true);
                            }
                        } else if (command[1].toLowerCase().equals("-s")) {
                            try {
                                int i = Integer.parseInt(command[2]) - 1;
                                int id = thisModel.getStudent().get(i).getId();
                                String na = thisModel.getStudent().get(i).getName();
                                String pa = thisModel.getStudent().get(i).getPatronymic();
                                String su = thisModel.getStudent().get(i).getSurname();
                                String da = thisModel.getStudent().get(i).getDate();
                                thisView.printStudent(id, na, pa, su, da);

                            } catch (NumberFormatException e) {
                                thisView.printError(0);
                            } catch (IndexOutOfBoundsException e) {
                                thisView.printError(1);
                            }
                        } else if (command[1].toLowerCase().equals("-g")) {
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
                                    String da = thisModel.getGroup(id).getStudents().get(i).getDate();
                                    thisView.printStudent(ids, na, pa, su, da);
                                }

                            } catch (NumberFormatException e) {
                                thisView.printError(0);
                            } catch (NullPointerException e) {
                                thisView.printError(2);
                            }
                        }

                    } catch (ArrayIndexOutOfBoundsException e) {
                        thisView.printError(3);
                    }
                    break;
                case "help":
                    thisView.printHelp();
                    break;
                case "exit":
                    exit = "exit";
                    thisModel.saveXML("src\\xml\\test.xml");
                    break;
                default:
                    thisView.printError(0);
                    break;
            }
        }
    }


    public String[] parseCommand(String s) {
        String[] st = s.trim().split("\\s+");

        return st;
    }

}
