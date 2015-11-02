public class View {

    View() {
        System.out.println("Введите команду [help] для справки.");

    }

    enum Error{
        HELP,
        STUDENT_NOT_FOUND,
        GROUP_NOT_FOUND,
        INVALID_SYNTAX,
        UNKNOWN_ERROR
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

    public void printConfirm(Boolean b, String t) {
        switch (t) {
            case "-s":
                if (b) System.out.println("Студент добавлен");
                else System.out.println("Данный студент уже имеется в базе");
                break;
            case "-g":
                if (b) System.out.println("Группа добавлена");
                else System.out.println("Данная группа уже имеется в базе");
                break;
            case "-stg":
                if (b) System.out.println("Студент добавлен в группу");
                else System.out.println("Ошибка! Студента {группы} не существует или он уже содержится в группе");
                break;
            default:
                printError(Error.UNKNOWN_ERROR);


        }
    }

}
