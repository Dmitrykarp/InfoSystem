import java.util.ArrayList;


public class View {
    String s;

    View() {
        System.out.println("Введите команду [help] для справки.");

    }

    public void printConsole(){
        System.out.print(">");
    }

    public void printAllStudent(ArrayList<Student> st) {
        for (int i=0; i<st.size(); i++){
            System.out.print(st.get(i).getId() + " " +st.get(i).getName() +" ");
            System.out.print(st.get(i).getPatronymic() +" " );
            System.out.print(st.get(i).getSurname() +" " );
            System.out.print(st.get(i).getDate() +" " );
            System.out.println();
        }
    }

    public void printAllGroup(ArrayList<Group> gr) {

    }

    public void printStudent(Student st) {

    }

    public void printGroup(Group gr) {

    }

    public void printError() {
        System.out.println("Ошибка! Введите команду [help] для справки.");
    }

    public void printHelp() {
        System.out.println("ADD - ДОБАВЛЕНИЕ ЭЛЕМЕНТА");
        System.out.println("ADD -s [имя] [фамилия] [отчество] [дата]");
        System.out.println("ADD -g [номер] [факультет]");
        System.out.println("ADD -stg [ID студента] [ID группы]");
    }

    public void printConfirm (Boolean b, String t){
        switch (t){
            case "-s":
                if(b) System.out.println("Студент добавлен");
                else System.out.println("Данный студент уже имеется в базе");
                break;
            case "-g":
                if(b) System.out.println("Группа добавлена");
                else System.out.println("Данная группа уже имеется в базе");
                break;
            case "-stg":
                if(b) System.out.println("Студент добавлен в группу");
                else System.out.println("Ошибка! Студента {группы} не существует или он уже содержится в группе");
                break;
            default:
                System.out.println("Неизвестная ошибка");


        }
    }

}
