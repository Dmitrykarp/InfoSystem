import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


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

    Model() {
        counter = 1;
    }

    public Model loadXML(String path, Model m) throws JAXBException {
        JAXBContext jaxbCtx = JAXBContext.newInstance(m.getClass());
        Unmarshaller um = jaxbCtx.createUnmarshaller();
        m = (Model) um.unmarshal(new File(path));
        ArrayList<Student> grSt = new ArrayList<Student>();
        for (int i = 0; i < m.getGroups().size(); i++) {
            grSt.addAll(m.getGroups().get(i).getStudents());
        }
        for (int i = 0; i < grSt.size(); i++) {
            m.addStudent(grSt.get(i).getId(), grSt.get(i).getName(), grSt.get(i).getSurname(), grSt.get(i).getPatronymic(), grSt.get(i).getDate());
        }
        return m;

    }

    public void loadZIP(String path) {
        try {
            ZipInputStream in = new ZipInputStream(new FileInputStream(path));
            ZipEntry entry = in.getNextEntry();
            String targetfile = "src\\xml\\test.xml";
            OutputStream out = new FileOutputStream(targetfile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void saveZIP(String path) {
        byte[] buf = new byte[1024];
        try {
            String target = "src\\test.zip";
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(target));
            FileInputStream in = new FileInputStream(path);
            out.putNextEntry(new ZipEntry(path));
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println(e);
        }
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


    public void addStudent(String n, String s, String p, Date d) {
        counter = this.students.size() + 1;
        Student student = new Student(counter, n, s, p, d);
        int tempStud = 0;
        for (int i = 0; i < students.size(); i++)
            if (students.get(i).equals(student))
                tempStud++;
        if (tempStud == 0) {
            counter++;
            students.add(student);
        } else throw new RuntimeException();
    }

    public void addStudent(int id, String n, String s, String p, Date d) {
        Student student = new Student(id, n, s, p, d);
        int tempStud = 0;
        for (int i = 0; i < students.size(); i++)
            if (students.get(i).equals(student))
                tempStud++;
        if (tempStud == 0) {
            students.add(student);
        } else throw new RuntimeException();
    }

    public void delStudent(int n) {
        int temp = 0;
        Iterator it = students.iterator();
        while (it.hasNext()) {
            Student item = (Student) it.next();
            if (item.getId() == n) {
                it.remove();
                temp++;
            }
        }
        for (Group i : group) {
            it = i.getStudents().iterator();
            while (it.hasNext()) {
                Student item = (Student) it.next();
                if (item.getId() == n) {
                    it.remove();
                    temp++;
                }
            }
        }
        if (temp == 0) throw new RuntimeException();
    }

    public void delGroup(int n) {
        int temp = 0;
        Iterator it = group.iterator();
        while (it.hasNext()) {
            Group item = (Group) it.next();
            if (item.getNumber() == n) {
                it.remove();
                temp++;
            }
        }
        if (temp == 0) throw new RuntimeException();
    }

    public void studentToGroup(int idStudent, int idGroup) {
        int tempSt = 0;
        int tempGr = 0;
        for (Student i : students) {
            if (i.getId() == idStudent) {
                for (Group j : group) {
                    for (Student n : j.getStudents()) {
                        if (i.equals(n))
                            tempSt++;
                    }
                }
            }
        }
        for (Group j : group) {
            if (j.getNumber() == idGroup) tempGr++;
        }
        if (tempSt == 0 & idStudent <= students.size() & tempGr > 0) {
            for (Student i : students) {
                if (i.getId() == idStudent) {
                    for (Group j : group) {
                        if (j.getNumber() == idGroup) j.addStudentToGroup(i);
                    }
                }
            }

        } else throw new RuntimeException();
    }

    public void addGroup(int n, String f) {
        Group groups = new Group(n, f);
        int tempCount = 0;
        for (int i = 0; i < group.size(); i++) {
            if (group.get(i).getNumber() == groups.getNumber())
                if (group.get(i).getFacult().equals(groups.getFacult()))
                    tempCount++;
        }
        if (tempCount == 0) {
            group.add(groups);
        } else throw new RuntimeException();
    }

    public void addGroup(Group g) {
        this.group.add(g);
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    public Student getStudent(int id) {
        return students.get(id);
    }

    public ArrayList<Group> getGroups() {
        if (group == null) {
            group = new ArrayList<Group>();
        }
        return this.group;
    }

    public Group getGroup(int id) {
        for (Group g : group) {
            if (g.getNumber() == id) return g;
        }
        throw new RuntimeException();
    }

    public void modifyStudent(int i, String n, String s, String p, Date d) {
        int temp = 0;
        for (Student st : students) {
            if (st.getId() == i) {
                st.setDate(d);
                st.setName(n);
                st.setPatronymic(p);
                st.setSurname(s);
                temp++;
            }
        }
        for (Group gr : group) {
            for (Student st : gr.getStudents()) {
                if (st.getId() == i) {
                    st.setDate(d);
                    st.setName(n);
                    st.setPatronymic(p);
                    st.setSurname(s);
                    temp++;
                }
            }
        }
        if (temp == 0) throw new RuntimeException();
    }

    public void modifyGroup(int oldID, int newID, String f) {
        int temp = 0;
        for (Group gr : group) {
            if (gr.getNumber() == oldID) {
                gr.setFacult(f);
                gr.setNumber(newID);
                temp++;
            }
        }
        if (temp == 0) throw new RuntimeException();
    }

    public ArrayList<Student> searchStudent(String find) {
        int temp = 0;
        ArrayList<Student> stTemp = new ArrayList<Student>();
        if (find.contains("*")) {
            find = find.replace("*", ".");
        } else if (find.contains("?")) {
            find = find.replace("?", "[а-я]*");
        }
        Pattern p = Pattern.compile("^" + find.toLowerCase() + "$");
        Matcher m;
        for (Student st : students) {
            m = p.matcher(st.getName().toLowerCase());
            if (m.matches()) {
                stTemp.add(st);
                temp++;
            } else {
                m = p.matcher(st.getPatronymic().toLowerCase());
                if (m.matches()) {
                    stTemp.add(st);
                    temp++;
                } else {
                    m = p.matcher(st.getSurname().toLowerCase());
                    if (m.matches()) {
                        stTemp.add(st);
                        temp++;
                    }
                }
            }
        }
        if (temp == 0) throw new RuntimeException();
        else return stTemp;
    }


    public ArrayList<Group> searchGroup(String find) {
        int temp = 0;
        ArrayList<Group> grTemp = new ArrayList<Group>();
        if (find.contains("*")) {
            find = find.replace("*", ".");
        } else if (find.contains("?")) {
            find = find.replace("?", "[а-я]*");
        }
        Pattern p = Pattern.compile("^" + find.toLowerCase() + "$");
        Matcher m;
        for (Group gr : group) {
            m = p.matcher(gr.getFacult().toLowerCase());
            if (m.matches()) {
                grTemp.add(gr);
                temp++;
            }

        }
        if (temp == 0) throw new RuntimeException();
        else return grTemp;
    }

    public Model fileToFile(String path, Model m) throws JAXBException {
        JAXBContext jaxbCtx = JAXBContext.newInstance(m.getClass());
        Unmarshaller um = jaxbCtx.createUnmarshaller();
        Model mTemp = new Model();
        mTemp = (Model) um.unmarshal(new File(path));
        mTemp = equalStudents(mTemp);
        Iterator iteratorGroupM = m.getGroups().iterator();
        while (iteratorGroupM.hasNext()) {
            Group itemGroupM = (Group) iteratorGroupM.next();
            Iterator groupIteratorTemp = mTemp.getGroups().iterator();
            while (groupIteratorTemp.hasNext()) {
                Group itemGroupTemp = (Group) groupIteratorTemp.next();
                if (itemGroupM.getNumber() == itemGroupTemp.getNumber() & itemGroupM.getFacult().equals(itemGroupTemp.getFacult())) {
                    groupIteratorTemp.remove();
                } else {
                    Iterator iteratorStudentM = m.getStudents().iterator();
                    while (iteratorStudentM.hasNext()) {
                        Student itemStudentM = (Student) iteratorStudentM.next();
                        Iterator iteratotStudentTemp = itemGroupTemp.getStudents().iterator();
                        while (iteratotStudentTemp.hasNext()) {
                            Student itemStTemp = (Student) iteratotStudentTemp.next();
                            if (itemStudentM.equals(itemStTemp, 1)) {
                                iteratotStudentTemp.remove();
                            }
                        }
                    }

                }

            }
        }
        for (Group gr : mTemp.getGroups())
            for (Student st : gr.getStudents())
                st.setId(m.students.size() + 1);
        for (int i = 0; i < mTemp.getGroups().size(); i++) {
            m.addGroup(mTemp.getGroups().get(i));
            for (int j = 0; j < mTemp.getGroups().get(i).getStudents().size(); j++) {
                String n = mTemp.getGroups().get(i).getStudents().get(j).getName();
                String p = mTemp.getGroups().get(i).getStudents().get(j).getPatronymic();
                String s = mTemp.getGroups().get(i).getStudents().get(j).getSurname();
                Date d = mTemp.getGroups().get(i).getStudents().get(j).getDate();
                m.addStudent(n, s, p, d);
            }
        }

        return m;
    }

    public Model equalStudents(Model m) {
        ArrayList<Student> grSt = new ArrayList<Student>();
        for (int i = 0; i < m.getGroups().size(); i++) {
            grSt.addAll(m.getGroups().get(i).getStudents());
        }
        for (int i = 0; i < grSt.size(); i++) {
            m.addStudent(grSt.get(i).getName(), grSt.get(i).getSurname(), grSt.get(i).getPatronymic(), grSt.get(i).getDate());
        }
        return m;
    }
}
