import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс <code>View</code> оповещает пользователя о последствиях ввода его команд.
 *
 * @author Karpenko Dmitry
 */
public class View {
    SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * Конструктор выводит сообщение о помощи.
     */
    View() {
        System.out.println("Введите команду [help] для справки.");

    }

    /**
     * Перечисление всех возможных ошибок.
     */
    enum Error {
        HELP,
        STUDENT_NOT_FOUND,
        GROUP_NOT_FOUND,
        INVALID_SYNTAX,
        FORBIDDEN_SYMBOLS,
        UNKNOWN_ERROR,
        STUDENT_ALREADY_DB,
        STUDENT_ALREADY_GROUP,
        STUDENT_ADD_DATE,
        STUDENT_FORMAT,
        GROUP_FORMAT,
        GROUP_ALREADY,
        FILE_NOT_FOUND,
        FILE_ARCHIVE_NOT_FOUND,
        FILE_XML_NOT_FOUND

    }

    /**
     * Перечисление результатов всех удачных комманд.
     */
    enum Confirm {
        STUDENT_ADD,
        GROUP_ADD,
        STUDENT_IN_GROUP,
        STUDENT_DELL,
        GROUP_DELL,
        STUDENT_MOD,
        GROUP_MOD,
        MERGER,
        COMPLETE


    }

    enum Help {
        CONSOLE,
        ADD,
        ADD_STUDENT_NAME,
        ADD_STUDENT_PATR,
        ADD_STRUDENT_SURN,
        ADD_STUDENT_DATE,
        GROUP_NUMBER,
        GROUP_NUMBER_NEW,
        ADD_GROUP_FACULT,
        ID_STUDENT,
        DEL,
        MOD,
        VIEW,
        FIND,
        PATH,
        FIND_STRING

    }

    /**
     * Выводит символ начала ввода команды для пользователя.
     */
    public void printConsole(Help console) {
        switch (console){
            case CONSOLE:
                System.out.print(">");
                break;
            case ADD:
                System.out.println("Введите S для добавления студентов.");
                System.out.println("Введите G для добавления группы.");
                System.out.println("Введите STG для добавления студента в группу.");
                System.out.println("Для выхода введите EXIT.");
                break;
            case ADD_STUDENT_NAME:
                System.out.println("Введите имя студента:");
                break;
            case ADD_STUDENT_PATR:
                System.out.println("Введите отчество студента:");
                break;
            case ADD_STRUDENT_SURN:
                System.out.println("Введите фамилию студента:");
                break;
            case ADD_STUDENT_DATE:
                System.out.println("Введите дату зачисления студента в формате: ДД.ММ.ГГГГ");
                break;
            case GROUP_NUMBER:
                System.out.println("Введите номер группы:");
                break;
            case GROUP_NUMBER_NEW:
                System.out.println("Введите новый номер группы:");
                break;
            case ADD_GROUP_FACULT:
                System.out.println("Введите факультет:");
                break;
            case ID_STUDENT:
                System.out.println("Введите ID студента:");
                break;
            case DEL:
                System.out.println("Введите S для удаления студента.");
                System.out.println("Введите G для удаления группы.");
                System.out.println("Для выхода введите EXIT.");
                break;
            case MOD:
                System.out.println("Введите S для изменения студента.");
                System.out.println("Введите G для изменения группы.");
                System.out.println("Для выхода введите EXIT.");
                break;
            case VIEW:
                System.out.println("Введите ALLS для вывода всех студентов в БД.");
                System.out.println("Введите ALLG для вывода всех групп в БД.");
                System.out.println("Введите S для вывода информации по конкретному студенту.");
                System.out.println("Введите G для вывода информации по конкретной группе.");
                System.out.println("Для выхода введите EXIT.");
                break;
            case FIND:
                System.out.println("Введите S для поиска студента.");
                System.out.println("Введите G для поиска группы.");
                System.out.println("Для выхода введите EXIT.");
                break;
            case FIND_STRING:
                System.out.println("Укажите данные для поиска.");
                System.out.println("В параметре поиска можно указать [*] для неизвестного символа");
                System.out.println("или [?] для неизвестного набора символов.");
                break;
            case PATH:
                System.out.println("Укажите путь к необходимому файлу:");
                break;




        }
    }

    /**
     * Метод выводит на консоль определенного студента.
     *
     * @param id         Номер студента.
     * @param name       имя студента.
     * @param patronymic Отчество студента.
     * @param surname    Фамилия студента.
     * @param date       Дата зачисления студента.
     */
    public void printStudent(int id, String name, String patronymic, String surname, Date date) {
        System.out.println(id + " " + name + " " + patronymic + " " + surname + " " + format1.format(date));
    }

    /**
     * Метод выводит на консоль определенную группу.
     *
     * @param number Номер группы.
     * @param facult Факультет.
     * @param b      Указывает о необходимости печати студентов из группы. True - печатать, false -нет.
     */
    public void printGroup(int number, String facult, boolean b) {
        if (!b)
            System.out.println("Номер: " + number + ". Факультет: " + facult);
        else System.out.println("Номер: " + number + ". Факультет: " + facult + ". Студенты в группе:");
    }

    /**
     * Метод печатает ошибки на консоль.
     *
     * @param error Значение ошибки из перечисления <code>Error</code>.
     */
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
                System.out.println("Ошибка! Введите [help] для справки!");
                break;

            case FORBIDDEN_SYMBOLS:
                System.out.println("Ошибка! Разрешены только буквы, цифры и пробел!");
                break;

            case UNKNOWN_ERROR:
                System.out.println("Ошибка! Код ошибки не найден!");
                break;

            case STUDENT_ALREADY_DB:
                System.out.println("Ошибка! Данный студент уже имеется в базе!");
                break;

            case STUDENT_ADD_DATE:
                System.out.println("Ошибка! Неверный формат даты!");
                break;

            case STUDENT_ALREADY_GROUP:
                System.out.println("Ошибка! Студента {группы} не существует или он уже содержится в группе!");
                break;

            case STUDENT_FORMAT:
                System.out.println("Ошибка! Данные введены не корректно!");
                break;

            case GROUP_ALREADY:
                System.out.println("Ошибка! Данная группа уже имеется в базе!");
                break;

            case GROUP_FORMAT:
                System.out.println("Ошибка! Номер группы задан не корректно!");
                break;

            case FILE_NOT_FOUND:
                System.out.println("Ошибка! Файл имеет неверную структуру или отсутствует!");
                break;

            case FILE_ARCHIVE_NOT_FOUND:
                System.out.println("Ошибка! Архивный файл не найден!");
                break;

            case FILE_XML_NOT_FOUND:
                System.out.println("Ошибка! Файл с данными поврежден или не обнаружен!");
                break;

            default:
                System.out.println("Ошибка!");
        }
    }

    /**
     * Метод выводит на консоль справку.
     */
    public void printHelp() {
        System.out.println("-= ДОБАВЛЕНиЕ ЭЛЕМЕНТА =-");
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
        System.out.println("-= УДАЛЕНиЕ ЭЛЕМЕНТОВ =-");
        System.out.println("DEL -g [id Группы]");
        System.out.println("DEL -s [id Студента]");
        System.out.println();
        System.out.println("-= иЗМЕНЕНиЕ ЭЛЕМЕНТОВ =-");
        System.out.println("MOD -g [id изменяемой группы] [новый id] [факультет]");
        System.out.println("MOD -s [id Студента] [имя] [отчество] [фамилия] [дата]");
        System.out.println();
        System.out.println("-= ПОиСК ЭЛЕМЕНТОВ =-");
        System.out.println("FIND -g [факультет]");
        System.out.println("FIND -s [имя/отчество/фамилия]");
        System.out.println("        В параметре можно указать [*] для неизвестного символа");
        System.out.println("        или [?] для неизвестного набора символов.");
        System.out.println();
        System.out.println("-= ДОБАВЛЕНиЕ ДАННЫХ иЗ ФАЙЛА =-");
        System.out.println("FILE [путь к файлу, например С:\\1\\target.xml] ");

    }

    /**
     * Метод выводит на консоль подтверждение действия пользователя.
     *
     * @param confirm Значение из перечисления <code>Confirm</code>.
     */
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
            case COMPLETE:
                System.out.println("======КОМАНДА ВЫПОЛНЕНА УСПЕШНО======");
                break;

            default:
                printError(Error.UNKNOWN_ERROR);
        }
    }

}
