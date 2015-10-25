import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "model", propOrder = {
        "group"
})
@XmlRootElement
public class Model {
    @XmlTransient
    private ArrayList<Student> students = new ArrayList<Student>();
    private ArrayList<Group> group = new ArrayList<Group>();
    @XmlTransient
    private int counter;

    Model() throws JAXBException {
        //loadXML("C:\\test.xml", this);
        counter = 1;
    }

    public void loadXML(String path, Model m) throws JAXBException {
        JAXBContext jaxbCtx = JAXBContext.newInstance(this.getClass());
        Unmarshaller um = jaxbCtx.createUnmarshaller();
        Marshaller marshaller = jaxbCtx.createMarshaller();
        FileInputStream fis = null;
        try {
            //Пробуем связать поток ввода с файлом, откуда будем считывать XML.
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Start load");
        m = (Model) um.unmarshal(fis);
        marshaller.marshal(m, System.out);
        System.out.println("exit load");

    }

    public void saveXML(String path) throws JAXBException {
        JAXBContext jaxbCtx = JAXBContext.newInstance(this.getClass());
        Marshaller marshaller = jaxbCtx.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        File localNewFile = new File(path);
        FileOutputStream fos = null;
        try {
            //Связываем поток вывода с файлом.
            fos = new FileOutputStream(localNewFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Маршаллизируем экземпляр класса Model
        //в файл и на консоль.
        System.out.println("Start save");
        marshaller.marshal(this, fos);
        marshaller.marshal(this, System.out);
        System.out.println("END SAVE");


    }

    public void addStudent(String n, String s, String p, String d) {
        Student student = new Student(counter, n, s, p, d);
        counter++;
        students.add(student);
    }

    public void delStudent(int n) {

    }

    public void studentToGroup(int idStudent, int idGroup) {
        for (Student i : students) {
            if (i.getId() == idStudent) {
                for (Group j : group) {
                    if (j.getNumber() == idGroup) j.addStudentToGroup(i);
                }
            }
        }
    }

    public void modifyStudent(int i, String n, String s, String p) {

    }

    public void addGroup(int n, String f) {
        Group groups = new Group(n, f);
        group.add(groups);

    }

    public void delGroup(int n) {

    }

    public List<Group> getGroup() {
        if (group == null) {
            group = new ArrayList<Group>();
        }
        return this.group;
    }

    public void modifyGroup(int i, String f, Student st) {

    }

    public Student searchStudent(String name) {
        return null;
    }

    public Student searchStudent(String name, String patr) {
        return null;
    }

    public Student searchStudent(int id) {
        return null;
    }

    public Group searchGroup(int id) {
        return null;
    }

    public Group searchGroup(String facult) {
        return null;
    }

    public void fileToFile(String path) {

    }



}
