import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.IllegalFormatException;

/**
 * Класс описывает сущность "Студент".
 *
 * @author Karpenko Dmitry
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "student", propOrder = {
        "id",
        "name",
        "patronymic",
        "surname",
        "date"
})
public class Student {
    private String name;
    @XmlElement(required = true)
    private String surname;
    @XmlElement(required = true)
    private String patronymic;
    @XmlElement(required = true)
    private int id;
    @XmlElement(required = true)
    private Date date;

    /**
     * Конструктору необходимо явно указать данные студента.
     *
     * @param id Номер студента.
     * @param name Uмя студента.
     * @param surname Фамилия студента.
     * @param patronymic Отчество студента.
     * @param date Дата зачисления студента.
     */
    Student(int id, String name, String surname, String patronymic, Date date) {
        setName(name);
        setSurname(surname);
        setPatronymic(patronymic);
        setDate(date);
        setId(id);
    }

    Student() {
    // Срабатывает при загрузке из XML файла.
    }

    /**
     * Метод выводит номер студента.
     *
     * @return Номер студента.
     */
    public int getId() {
        return id;
    }

    /**
     * Метод устанавливает номер студенту.
     *
     * @param value Номер студента.
     */
    public void setId(int value) {
         this.id = value;
    }

    /**
     * Метод выводит имя студента.
     *
     * @return имя студента.
     */
    public String getName() {
        return name;
    }

    /**
     * Метод задает имя студента.
     *
     * @param value Uмя студента.
     */
    public void setName(String value) {
       this.name = value;
    }

    /**
     * Метод возвращает отчество студента.
     *
     * @return Отчество студента.
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Метод устанавливает отчество студента.
     *
     * @param value Отчество студента.
     */
    public void setPatronymic(String value) {
        this.patronymic = value;
    }

    /**
     * Метод возвращает фамилию студента.
     *
     * @return Фамилия студента.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Метод задает фамилию студента.
     *
     * @param value Фамилия студента.
     */
    public void setSurname(String value) {
        this.surname = value;
    }

    /**
     * Метод возвращает дату зачисления студента.
     *
     * @return Дата зачисления студента.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Метод устанавливает дату зачисления студента.
     *
     * @param value Дата зачисления студента.
     */
    public void setDate(Date value) {
        this.date = value;
    }

    /**
     * Метод сравнивает двух студентов на идентичность.
     *
     * @param o Экземпляр Student.
     *
     * @return True - если эквиваленты, иначе false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        if (id == ((Student) o).getId())
            if (name.equals(((Student) o).getName()))
                if (surname.equals(((Student) o).getSurname()))
                    if (patronymic.equals(((Student) o).getPatronymic()))
                        return true;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        if (surname != null ? !surname.equals(student.surname) : student.surname != null) return false;
        if (patronymic != null ? !patronymic.equals(student.patronymic) : student.patronymic != null) return false;
        return !(date != null ? !date.equals(student.date) : student.date != null);
    }

    /**
     * Метод сравнивает двух студентов на идентичность, без учета номера студента.
     * Метод используется при загрузке данных из файла.
     *
     * @param o Экземпляр Student.
     *
     * @return True - если эквиваленты, иначе false.
     */
    public boolean equalsWitchoutId(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        if (name.equals(((Student) o).getName()))
            if (surname.equals(((Student) o).getSurname()))
                if (patronymic.equals(((Student) o).getPatronymic()))
                    return true;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        if (surname != null ? !surname.equals(student.surname) : student.surname != null) return false;
        if (patronymic != null ? !patronymic.equals(student.patronymic) : student.patronymic != null) return false;
        return !(date != null ? !date.equals(student.date) : student.date != null);
    }
}

