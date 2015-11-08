import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.IllegalFormatException;


/**
 * Класс описывает сущность группы.
 *
 * @author Karpenko Dmitry
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "group", propOrder = {
        "number",
        "facult",
        "students"
})
public class Group {
    private int number;
    @XmlElement(required = true)
    private String facult;
    private ArrayList<Student> students;

    /**
     * Конструктору необходимо явно указать данные группы.
     *
     * @param number Номер группы.
     * @param facult Факультет.
     */
    Group(int number, String facult) {
        setFacult(facult);
        setNumber(number);
        students = new ArrayList<Student>();
    }

    Group() {
    //Необходим при загрузке из XML файла.
    }

    /**
     * Метод возвращает номер группы.
     *
     * @return Номер группы.
     */
    public int getNumber() {

        return number;
    }

    /**
     * Метод задает номер группы.
     *
     * @param value Номер группы должен быть больше нуля.
     */
    public void setNumber(int value) {
        if(value <1) throw new IllegalArgumentException();
        this.number = value;
    }

    /**
     * Метод возвращает название факультета.
     *
     * @return Название факультета.
     */
    public String getFacult() {

        return facult;
    }

    /**
     * Метод устанавливает название факультета.
     *
     * @param value Название факультета. Параметр не пустой и не null.
     */
    public void setFacult(String value) {
        if(value == null || value.trim().isEmpty()) throw new IllegalArgumentException();
        else  this.facult = value;
    }

    /**
     * Метод возвращает список всех студентов, состоящих в группе.
     *
     * @return Коллекция студентов.
     */
    public ArrayList<Student> getStudents() {
        if (students == null) {
            students = new ArrayList<Student>();
        }

        return this.students;
    }

    /**
     * Метод добавляет студента в группу.
     *
     * @param student Экземпляр Student.
     */
    public void addStudentToGroup(Student student) {
        students.add(student);
    }
}
