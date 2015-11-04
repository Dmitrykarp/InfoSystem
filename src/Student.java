import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;


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


    Student(int i, String name, String surname, String patronymic, Date date) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.date = date;
        this.id = i;


    }

    Student() {

    }

    public String getStudent(int id) {

        return null;
    }

    public int getId() {
        return id;
    }


    public void setId(int value) {
        this.id = value;
    }


    public String getName() {
        return name;
    }


    public void setName(String value) {
        this.name = value;
    }


    public String getPatronymic() {
        return patronymic;
    }


    public void setPatronymic(String value) {
        this.patronymic = value;
    }


    public String getSurname() {
        return surname;
    }


    public void setSurname(String value) {
        this.surname = value;
    }


    public Date getDate() {
        return date;
    }


    public void setDate(Date value) {
        this.date = value;
    }


}

