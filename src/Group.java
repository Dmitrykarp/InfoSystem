import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "group", propOrder = {
        "number",
        "facult",
        "students"
})
public class Group {
    protected int number;
    @XmlElement(required = true)
    protected String facult;
    protected List<Student> students = new ArrayList<Student>();

    Group(int number, String facult){
        this.facult=facult;
        this.number=number;
    }


    public int getNumber() {
        return number;
    }


    public void setNumber(int value) {
        this.number = value;
    }


    public String getFacult() {
        return facult;
    }


    public void setFacult(String value) {
        this.facult = value;
    }


    public List<Student> getStudents() {
        if (students == null) {
            students = new ArrayList<Student>();
        }
        return this.students;
    }

    public void addStudentToGroup (Student st){
        students.add(st);

    }


}
