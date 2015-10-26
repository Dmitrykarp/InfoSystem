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
            System.out.print("Номер: " + st.get(i).getId() + " ");
            System.out.print("Имя: " +st.get(i).getName() +" " );
            System.out.print("Отчество: " +st.get(i).getPatronymic() +" " );
            System.out.print("Фамилия: " +st.get(i).getSurname() +" " );
            System.out.print("Дата: " +st.get(i).getDate() +" " );
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
        System.out.println("ADD [-s] Добавление студента. Формат: ");
        System.out.println("ADD -s [имя] [фамилия] [отчество] [дата]");
        System.out.println();
        System.out.println("ADD [-g] Добавление группы. Формат: ");
        System.out.println("ADD -g [номер] [факультет]");
        System.out.println();
        System.out.println("ADD [-stg] Добавление студента в группу. Формат: ");
        System.out.println("ADD -stg [ID студента] [ID группы]");
    }


}
