import java.text.SimpleDateFormat;
import java.util.Date;

public class View {
    SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");

    View() {
        System.out.println("Введите команду [help] для справки.");

    }

    enum Error {
        HELP,
        STUDENT_NOT_FOUND,
        GROUP_NOT_FOUND,
        INVALID_SYNTAX,
        UNKNOWN_ERROR,
        STUDENT_ALREADY_DB,
        STUDENT_ALREADY_GROUP,
        STUDENT_ADD_DATE,
        GROUP_ALREADY,
        FILE_NOT_FOUND

    }

    enum Confirm {
        STUDENT_ADD,
        GROUP_ADD,
        STUDENT_IN_GROUP,
        STUDENT_DELL,
        GROUP_DELL,
        STUDENT_MOD,
        GROUP_MOD,
        MERGER

    }

    public void printConsole() {
        System.out.print(">");
    }

    public void printStudent(int id, String name, String patronymic, String surname, Date date) {
        System.out.println(id + " " + name + " " + patronymic + " " + surname + " " + format1.format(date));
    }

    public void printGroup(int number, String facult, boolean b) {
        if (b)
            System.out.println("Номер: " + number + ". Факультет: " + facult);
        else System.out.println("Номер: " + number + ". Факультет: " + facult + ". Студенты в группе:");
    }

    public void printError(Error error) {
        switch (error) {
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

            case STUDENT_ADD_DATE:
                System.out.println("Ошибка! Введите дату в формате: ДД.ММ.ГГГГ!");
                break;

            case STUDENT_ALREADY_GROUP:
                System.out.println("Ошибка! Студента {группы} не существует или он уже содержится в группе!");
                break;

            case GROUP_ALREADY:
                System.out.println("Ошибка! Данная группа уже имеется в базе!");
                break;

            case FILE_NOT_FOUND:
                System.out.println("Ошибка! Неудается найти указанный файл!");
                break;

            default:
                System.out.println("Ошибка!");
        }
    }

    public void printHelp() {
        System.out.println("-= ДОБАВЛЕНИЕ ЭЛЕМЕНТА =-");
        System.out.println("ADD -s [имя] [отчество] [фамилия] [дата: dd.mm.yyyy]");
        System.out.println("ADD -g [номер] [факультет]");
        System.out.println("ADD -stg [ID студента] [ID группы]");
        System.out.println();
        System.out.println("-= ПОКАЗ ЭЛЕМЕНТОВ =-");
        System.out.println("VIEW -allg");
        System.out.println("VIEW -alls");
        System.out.println("VIEW -s [id] ");
        System.out.println("VIEW -g [номер]");
        System.out.println();
        System.out.println("-= УДАЛЕНИЕ ЭЛЕМЕНТОВ =-");
        System.out.println("DEL -g [id Группы]");
        System.out.println("DEL -s [id Студента]");
        System.out.println();
        System.out.println("-= ИЗМЕНЕНИЕ ЭЛЕМЕНТОВ =-");
        System.out.println("MOD -g [id изменяемой группы] [новый id] [факультет]");
        System.out.println("MOD -s [id Студента] [имя] [отчество] [фамилия] [дата]");
        System.out.println();
        System.out.println("-= ПОИСК ЭЛЕМЕНТОВ =-");
        System.out.println("FIND -g [факультет]");
        System.out.println("FIND -s [имя/отчество/фамилия]");
        System.out.println("        В параметре можно указать [*] для неизвестного символа");
        System.out.println("        или [?] для неизвестного набора символов.");
        System.out.println();
        System.out.println("-= ДОБАВЛЕНИЕ ДАННЫХ ИЗ ФАЙЛА =-");
        System.out.println("FILE [путь к файлу, например С:\\1\\target.xml] ");

    }

    public void printConfirm(Confirm confirm) {
        switch (confirm) {
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

            case STUDENT_MOD:
                System.out.println("Студент успешно изменен");
                break;

            case GROUP_MOD:
                System.out.println("Группа успешно изменена");
                break;

            case MERGER:
                System.out.println("Данные из файла добавлены успешно");
                break;

            default:
                printError(Error.UNKNOWN_ERROR);
        }
    }

}
