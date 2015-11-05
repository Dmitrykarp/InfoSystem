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

/**
 * Класс описывает логику работы с данными.
 *
 * @author Karpenko Dmitry
 */
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

    /**
     * Конструктор инициализирует счетчик студентов.
     */
    Model() {
        counter = 1;
    }

    /**
     * Метод загружает данные из XML файла.
     *
     * @param path Путь к файлу.
     * @param model Экземпляр Model, куда будут загружены данные.
     *
     * @return Экземпляр Model с обновленными данными.
     *
     * @throws JAXBException Возникает при неверном указании источника данных или его отсутствии.
     */
    public Model loadXML(String path, Model model) throws JAXBException {
        JAXBContext jaxbCtx = JAXBContext.newInstance(model.getClass());
        Unmarshaller um = jaxbCtx.createUnmarshaller();
        model = (Model) um.unmarshal(new File(path));
        ArrayList<Student> studentsInGroup = new ArrayList<Student>();

        /** Переносим студентов из конкретных групп в общую базу */
        for (int i = 0; i < model.getGroups().size(); i++) {
            studentsInGroup.addAll(model.getGroups().get(i).getStudents());
        }
        for (int i = 0; i < studentsInGroup.size(); i++) {
            model.addStudent(studentsInGroup.get(i).getId(),
                    studentsInGroup.get(i).getName(),
                    studentsInGroup.get(i).getSurname(),
                    studentsInGroup.get(i).getPatronymic(),
                    studentsInGroup.get(i).getDate());
        }

        return model;
    }

    /**
     * /**
     * Метод выгружает XML файл из ZIP-архива.
     *
     * @param path Путь к ZIP-архиву.
     *
     * @throws IOException Возникает если путь к файлу не верный.
     */
    public void loadZIP(String path) throws IOException {

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


    }

    /**
     *Метод сжимает XML файл в ZIP-архив.
     *
     * @param path Путь до XML файла.
     *
     * @throws IOException Возникает если путь к файлу не верный.
     */
    public void saveZIP(String path) throws IOException {
        byte[] buf = new byte[1024];


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


    }

    /**
     * Метод сохраняет данные в XML файл.
     *
     * @param path Путь до конечного XML файла.
     *
     * @throws JAXBException Возникает при неверном указании источника данных или его отсутствии.
     */
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

    /**
     * Метод добавляет студента в общий список студентов.
     * Метод необходим для добавления студентов в момент работы с данными в реальном времени.
     *
     * @param name Uмя студента.
     * @param surname Фамилия студента.
     * @param patronymic Отчество студента.
     * @param date Дата зачисления студента.
     */
    public void addStudent(String name,
                           String surname,
                           String patronymic,
                           Date date) {
        counter = this.students.size() + 1;
        Student student = new Student(counter, name, surname, patronymic, date);
        int tempStud = 0;

        for (int i = 0; i < students.size(); i++)
            if (students.get(i).equals(student))
                tempStud++;

        if (tempStud == 0) {
            counter++;
            students.add(student);
        } else throw new RuntimeException();
    }

    /**
     * Метод добавляет студента в общий список студентов.
     * Метод необходим при добавлении из XML файла.
     *
     * @param id Номер студента.
     * @param name Uмя студента.
     * @param surname Фамилия студента.
     * @param patronymic Отчество студента.
     * @param date Дата зачисления студента.
     */
    public void addStudent(int id,
                           String name,
                           String surname,
                           String patronymic,
                           Date date) {
        Student student = new Student(id, name, surname, patronymic, date);
        int tempStud = 0;

        for (int i = 0; i < students.size(); i++)
            if (students.get(i).equals(student))
                tempStud++;

        if (tempStud == 0) {
            students.add(student);
        } else throw new RuntimeException();
    }

    /**
     * Метод удаляет студента из группы и из общего списка студентов.
     *
     * @param id Номер студента.
     */
    public void delStudent(int id) {
        int temp = 0;

        /** Удаляем из общего списка */
        Iterator iterator = students.iterator();

        while (iterator.hasNext()) {
            Student item = (Student) iterator.next();
            if (item.getId() == id) {
                iterator.remove();
                temp++;
            }
        }

        /** Удаляем из списка групп */
        for (Group i : group) {
            iterator = i.getStudents().iterator();
            while (iterator.hasNext()) {
                Student item = (Student) iterator.next();
                if (item.getId() == id) {
                    iterator.remove();
                    temp++;
                }
            }
        }

        if (temp == 0) throw new RuntimeException();
    }

    /**
     * Метод удаляет группу.
     *
     * @param number Номер группы.
     */
    public void delGroup(int number) {
        int temp = 0;
        Iterator it = group.iterator();
        while (it.hasNext()) {
            Group item = (Group) it.next();
            if (item.getNumber() == number) {
                it.remove();
                temp++;
            }
        }

        if (temp == 0) throw new RuntimeException();
    }

    /**
     * Метод добавляет студента в группу.
     *
     * @param idStudent Номер студента.
     * @param idGroup Номер группы.
     */
    public void studentToGroup(int idStudent, int idGroup) {
        int tempSt = 0;
        int tempGr = 0;
        /** Поиск идентичного студента */
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
        /** Поиск необходимой группы */
        for (Group j : group) {
            if (j.getNumber() == idGroup) tempGr++;
        }
        /** Добавление студента в группу */
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

    /**
     * Метод создает группу.
     *
     * @param number Номер группы.
     * @param facult Название факультета.
     */
    public void addGroup(int number, String facult) {
        Group groups = new Group(number, facult);
        int tempCount = 0;
        /** Поиск идентичной группы */
        for (int i = 0; i < group.size(); i++) {
            if (group.get(i).getNumber() == groups.getNumber())
                if (group.get(i).getFacult().equals(groups.getFacult()))
                    tempCount++;
        }
        /** Добавление группы */
        if (tempCount == 0) {
            group.add(groups);
        } else throw new RuntimeException();
    }

    /**
     * Метод добавляет группу.
     * Метод необходим для загрузки данных из XML файла.
     *
     * @param g Экземпляр Group.
     */
    public void addGroup(Group g) {
        this.group.add(g);
    }

    /**
     * Метод возвращает общий список студентов.
     *
     * @return Список экземпляров Group.
     */
    public ArrayList<Student> getStudents() {

        return this.students;
    }

    /**
     * Метод возвращает конкретного студента из списка.
     *
     * @param id Номер студента.
     *
     * @return Экземпляр Student.
     */
    public Student getStudent(int id) {
        return students.get(id);
    }

    /**
     * Метод возвращает список групп.
     *
     * @return Список экземпляров Group.
     */
    public ArrayList<Group> getGroups() {
        if (group == null) {
            group = new ArrayList<Group>();
        }
        return this.group;
    }

    /**
     * Метод возвращает конкретную группу
     *
     * @param id Номер группы.
     *
     * @return Экземпляр Group.
     */
    public Group getGroup(int id) {
        for (Group g : group) {
            if (g.getNumber() == id) return g;
        }
        throw new RuntimeException();
    }

    /**
     * Метод изменяет данные студента в общем списке и в группе, которой находится.
     *
     * @param id Номер студента.
     * @param name Uмя студента.
     * @param surname Фамилия студента.
     * @param patronymic Отчество студента.
     * @param date Дата зачисления студента.
     */
    public void modifyStudent(int id,
                              String name,
                              String surname,
                              String patronymic,
                              Date date) {
        int temp = 0;
        for (Student st : students) {
            if (st.getId() == id) {
                st.setDate(date);
                st.setName(name);
                st.setPatronymic(patronymic);
                st.setSurname(surname);
                temp++;
            }
        }

        for (Group gr : group) {
            for (Student st : gr.getStudents()) {
                if (st.getId() == id) {
                    st.setDate(date);
                    st.setName(name);
                    st.setPatronymic(patronymic);
                    st.setSurname(surname);
                    temp++;
                }
            }
        }
        if (temp == 0) throw new RuntimeException();
    }

    /**
     * Метод изменяет данные группы.
     *
     * @param oldID Текущий номер группы.
     * @param newID Новый номер группы.
     * @param facult Новое название факультета.
     */
    public void modifyGroup(int oldID, int newID, String facult) {
        int temp = 0;

        for (Group gr : group) {
            if (gr.getNumber() == oldID) {
                gr.setFacult(facult);
                gr.setNumber(newID);
                temp++;
            }
        }

        if (temp == 0) throw new RuntimeException();
    }

    /**
     * Метод осуществляет поиск студентов по входным параметрам.
     *
     * @param find Параметры поиска.
     *             Может включать [*] - для пропуска символа и [?] - для пропуска нескольких символов.
     *
     * @return Список найденных студентов.
     */
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

    /**
     * Метод осуществляет поиск группы по заданным параметрам.
     *
     * @param find Параметры поиска.
     *             Может включать [*] - для пропуска символа и [?] - для пропуска нескольких символов.
     *
     * @return Список найденных групп.
     */
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

    /**
     * Метод добавляет данные из XML файла в сущесствующую модель.
     *
     * @param path Путь к XML файлу.
     * @param model Экземпляр Model, куда будут загружены данные.
     *
     * @return Экземпляр Model с обновленными данными.
     *
     * @throws JAXBException Возникает при неверном указании источника данных или его отсутствии.
     */
    public Model fileToFile(String path, Model model) throws JAXBException {
        JAXBContext jaxbCtx = JAXBContext.newInstance(model.getClass());
        Unmarshaller um = jaxbCtx.createUnmarshaller();
        Model mTemp = new Model();
        mTemp = (Model) um.unmarshal(new File(path));
        mTemp = addStudentInStudentList(mTemp);
        /** Удаляем одинаковые группы и студентов в группах */
        Iterator iteratorGroupM = model.getGroups().iterator();
        while (iteratorGroupM.hasNext()) {
            Group itemGroupM = (Group) iteratorGroupM.next();
            Iterator groupIteratorTemp = mTemp.getGroups().iterator();
            while (groupIteratorTemp.hasNext()) {
                Group itemGroupTemp = (Group) groupIteratorTemp.next();
                if ((itemGroupM.getNumber() == itemGroupTemp.getNumber()) &
                        itemGroupM.getFacult().equals(itemGroupTemp.getFacult())) {
                    groupIteratorTemp.remove();
                } else {
                    Iterator iteratorStudentM = model.getStudents().iterator();
                    while (iteratorStudentM.hasNext()) {
                        Student itemStudentM = (Student) iteratorStudentM.next();
                        Iterator iteratotStudentTemp = itemGroupTemp.getStudents().iterator();
                        while (iteratotStudentTemp.hasNext()) {
                            Student itemStTemp = (Student) iteratotStudentTemp.next();
                            if (itemStudentM.equalsWitchoutId(itemStTemp)) {
                                iteratotStudentTemp.remove();
                            }
                        }
                    }

                }

            }
        }
        /** Даем новые порядковые номера */
        for (Group gr : mTemp.getGroups())
            for (Student st : gr.getStudents())
                st.setId(model.students.size() + 1);
        /** Добавляем группы и студентов в модель */
        for (int i = 0; i < mTemp.getGroups().size(); i++) {
            model.addGroup(mTemp.getGroups().get(i));
            for (int j = 0; j < mTemp.getGroups().get(i).getStudents().size(); j++) {
                String name = mTemp.getGroups().get(i).getStudents().get(j).getName();
                String patronymic = mTemp.getGroups().get(i).getStudents().get(j).getPatronymic();
                String surname = mTemp.getGroups().get(i).getStudents().get(j).getSurname();
                Date date = mTemp.getGroups().get(i).getStudents().get(j).getDate();
                model.addStudent(name, surname, patronymic, date);
            }
        }

        return model;
    }

    /**
     * Метод переносит студентов из группы в общий список студентов.
     *
     * @param m Экземпляр Model для обновления списка студентов.
     *
     * @return Обновленный экземпляр Model.
     */
    public Model addStudentInStudentList(Model m) {
        ArrayList<Student> grSt = new ArrayList<Student>();

        for (int i = 0; i < m.getGroups().size(); i++) {
            grSt.addAll(m.getGroups().get(i).getStudents());
        }

        for (int i = 0; i < grSt.size(); i++) {
            m.addStudent(grSt.get(i).getName(),
                    grSt.get(i).getSurname(),
                    grSt.get(i).getPatronymic(),
                    grSt.get(i).getDate());
        }

        return m;
    }
}
