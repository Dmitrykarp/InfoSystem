

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;




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

        counter = 1;
    }

    public Model loadXML(String path, Model m) throws JAXBException {
        JAXBContext jaxbCtx = JAXBContext.newInstance(m.getClass());
        Unmarshaller um = jaxbCtx.createUnmarshaller();
        m = (Model) um.unmarshal(new File(path));
        m.students = equalStudents(m.students, m.group);
        return m;

    }

    public void saveXML(String path) throws JAXBException {
        JAXBContext jaxbCtx = JAXBContext.newInstance(this.getClass());
        Marshaller marshaller = jaxbCtx.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        File localNewFile = new File(path);
        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(localNewFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        marshaller.marshal(this, fos);


    }

    public boolean addStudent(String n, String s, String p, String d) {
        counter=this.students.size()+1;
        Student student = new Student(counter, n, s, p, d);
        int tempStud=0;
        for(int i=0; i<students.size(); i++)
            if(students.get(i).getName().equals(student.getName()))
                if(students.get(i).getPatronymic().equals(student.getPatronymic()))
                    if (students.get(i).getSurname().equals(student.getSurname()))
                        if (students.get(i).getDate().equals(student.getDate()))
                            tempStud++;

        if (tempStud==0) {
            counter++;
            students.add(student);
            return true;
        }else return false;
    }

    public void delStudent(int n) {

    }

    public boolean studentToGroup(int idStudent, int idGroup) {
        int tempSt=0;
        int tempGr=0;
        for(Student i: students){
            if (i.getId() == idStudent) {
                for (Group j : group) {
                    for (Student n :j.getStudents()){
                        if(i.equals(n))
                            tempSt++;
                    }
                }
            }
        }
        for(Group j: group){
            if(j.getNumber()==idGroup) tempGr++;
        }
        if(tempSt==0 & idStudent<=students.size() & tempGr>0){
            for (Student i : students) {
                if (i.getId() == idStudent) {
                    for (Group j : group) {
                        if (j.getNumber() == idGroup) j.addStudentToGroup(i);
                    }
                }
            } return true;
        }else return false;
    }

    public void modifyStudent(int i, String n, String s, String p, String d) {

    }

    public boolean addGroup(int n, String f) {
        Group groups = new Group(n, f);
        int tempCount=0;
        for(int i=0; i<group.size(); i++){
            if(group.get(i).getNumber()==groups.getNumber())
                if (group.get(i).getFacult().equals(groups.getFacult()))
                tempCount++;
        }
        if(tempCount==0) {
            group.add(groups);
            return true;
        }else return false;
    }

    public void delGroup(int n) {

    }

    public ArrayList<Student> getStudent() {
        return this.students;
    }

    public Student getStudent(int id){
        return students.get(id);
    }

    public ArrayList<Group> getGroup() {
        if (group == null) {
            group = new ArrayList<Group>();
        }
        return this.group;
    }

    public Group getGroup(int id){
        for(Group g: group){
            if(g.getNumber()==id) return g;
        }
        return null;
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

    public ArrayList<Student> equalStudents(ArrayList<Student> s, ArrayList<Group> g) {
        ArrayList<Student> tempSt = new ArrayList<Student>();
        ArrayList<Student> tempStud = new ArrayList<Student>();
        for (int i = 0; i < g.size(); i++) {
            tempSt.addAll(g.get(i).getStudents());
        }

        s.add(tempSt.get(0));
        tempStud.add(tempSt.get(0));

        for (int i = 0; i < tempSt.size(); i++) {
            for (int j = 0; j < s.size(); j++) {
                if (tempSt.get(i).getName() == s.get(j).getName()) {
                    if (tempSt.get(i).getPatronymic() == s.get(j).getPatronymic()) {
                        if (tempSt.get(i).getSurname() == s.get(j).getSurname()) {
                            if (tempSt.get(i).getDate() == s.get(j).getDate())
                                continue;
                        }
                    }
                } else tempStud.add(tempSt.get(i));
            }
        }
        this.counter=tempStud.size();
        return tempStud;
    }

}
