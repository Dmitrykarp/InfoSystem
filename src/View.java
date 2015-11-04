import java.text.SimpleDateFormat;
import java.util.Date;

public class View {
    SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");

    View() {
        System.out.println("Введите команду [help] для справки.");

    }

    enum Error{
        HELP,
        STUDENT_NOT_FOUND,
        GROUP_NOT_FOUND,
        INVALID_SYNTAX,
        UNKNOWN_ERROR,
        STUDENT_ALREADY_DB,
        STUDENT_ALREADY_GROUP,
        STUDENT_ADD_DATE,
        GROUP_ALREADY

    }

    enum Confirm{
        STUDENT_ADD,
        GROUP_ADD,
        STUDENT_IN_GROUP,
        STUDENT_DELL,
        GROUP_DELL

    }

    public void printConsole() {
        System.out.print(">");
    }

    //TODO поменять на ДАту
    public void printStudent(int id, String n, String pa, String su, Date da) {
        System.out.println(id + " " + n + " " + pa + " " + su + " " + format1.format(da));
    }


    public void printGroup(int id, String facult, boolean b) {
        if (b)
            System.out.println("Номер: " + id + ". Факультет: " + facult);
        else System.out.println("Номер: " + id + ". Факультет: " + facult + ". Студенты в группе:");
    }

    public void printError(Error e) {
        switch (e) {
            case HELP:
                System.out.println("Ошибка! Введите команду [help] для справки.");
                break;
            case STUDENT_NOT_FOUND:
                System.out.println("Ошибка! Студент не найден!");
                break;
            case GROUP_NOT_FOUND:
                System.out.println("Ошибка! Группа не найдена!");
                break;
            case INVALID_SYNTAX:
                System.out.println("Ошибка! Неверный синтаксис команды. Введите [help] для справки!");
                break;
            case UNKNOWN_ERROR:
                System.out.println("Ошибка! Код ошибки не найден!");
                break;
            case STUDENT_ALREADY_DB:
                System.out.println("Ошибка! Данный студент уже имеется в базе!");
                break;
            case  STUDENT_ADD_DATE:
                System.out.println("Ошибка! Введите дату в формате: ДД.ММ.ГГГГ!");
                break;
            case STUDENT_ALREADY_GROUP:
                System.out.println("Ошибка! Студента {группы} не существует или он уже содержится в группе!");
                break;
            case GROUP_ALREADY:
                System.out.println("Ошибка! Данная группа уже имеется в базе!");
                break;
            default:
                System.out.println("Ошибка!");
        }
    }

    public void printHelp() {
        System.out.println("ADD - ДОБАВЛЕНИЕ ЭЛЕМЕНТА");
        System.out.println("ADD -s [имя] [отчество] [фамилия] [дата: dd.mm.yyyy]");
        System.out.println("ADD -g [номер] [факультет]");
        System.out.println("ADD -stg [ID студента] [ID группы]");
        System.out.println();
        System.out.println("VIEW - ПОКАЗ ЭЛЕМЕНТОВ");
        System.out.println("VIEW -allg");
        System.out.println("VIEW -alls");
        System.out.println("VIEW -s [id] ");
        System.out.println("VIEW -g [номер]");
        System.out.println();
        System.out.println("DEL - УДАЛЕНИЕ ЭЛЕМЕНТОВ");
        System.out.println("DEL -g [id Группы]");
        System.out.println("DEL -s [id Студента]");

    }

    public void printConfirm(Confirm c) {
        switch (c) {
            case STUDENT_ADD:
                System.out.println("Студент добавлен");
                break;
            case GROUP_ADD:
                System.out.println("Группа добавлена");
                break;
            case STUDENT_IN_GROUP:
                System.out.println("Студент добавлен в группу");
                break;
            case STUDENT_DELL:
                System.out.println("Студент успешно удален");
                break;
            case GROUP_DELL:
                System.out.println("Группа успешно удалена");
                break;
            default:
                printError(Error.UNKNOWN_ERROR);


        }
    }

}
