public class View {

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
        GROUP_ALREADY

    }

    enum Confirm{
        STUDENT_ADD,
        GROUP_ADD,
        STUDENT_IN_GROUP
    }

    public void printConsole() {
        System.out.print(">");
    }

    public void printStudent(int id, String n, String pa, String su, String da) {
        System.out.println(id + " " + n + " " + pa + " " + su + " " + da);
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
        System.out.println("ADD -s [имя] [фамилия] [отчество] [дата]");
        System.out.println("ADD -g [номер] [факультет]");
        System.out.println("ADD -stg [ID студента] [ID группы]");
        System.out.println();
        System.out.println("VIEW - ПОКАЗ ЭЛЕМЕНТОВ");
        System.out.println("VIEW -allg");
        System.out.println("VIEW -alls");
        System.out.println("VIEW -s [id] ");
        System.out.println("VIEW -g [номер]");

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
            default:
                printError(Error.UNKNOWN_ERROR);


        }
    }

}
