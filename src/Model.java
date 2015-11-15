import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * Метод добавляет студента в общий список студентов.
     * Метод необходим для добавления студентов в момент работы с данными в реальном времени.
     *
     * @param name Uмя студента.
     * @param surname Фамилия студента.
     * @param patronymic Отчество студента.
     * @param date Дата зачисления студента.
     *
     * @throws UnsupportedOperationException Возникает при невозможности добавить студента.
     *         Студент уже имеется в базе.
     */
    public void addStudent(String name,
                           String surname,
                           String patronymic,
                           Date date) {
        counter = this.students.size() + 1;
        Student student = new Student(counter, name, surname, patronymic, date);
        int tempStud = 0;

        for (int i = 0; i < students.size(); i++)
            if (students.get(i).equalsWitchoutId(student))
                tempStud++;

        if (tempStud == 0) {
            counter++;
            students.add(student);
        } else throw new UnsupportedOperationException();
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
     *
     * @throws UnsupportedOperationException Возникает при невозможности добавить студента.
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
        } else throw new UnsupportedOperationException();
    }

    /**
     * Метод удаляет студента из группы и из общего списка студентов.
     *
     * @param id Номер студента.
     *
     * @throws UnsupportedOperationException Возникает при невозможности удалить студента.
     *          Например студент не найден.
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

        if (temp == 0) throw new UnsupportedOperationException();
    }

    /**
     * Метод удаляет группу.
     *
     * @param number Номер группы.
     *
     * @throws UnsupportedOperationException Возникает при невозможности удалить группу.
     *          Например группа не найдена.
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

        if (temp == 0) throw new UnsupportedOperationException();
    }

    /**
     * Метод добавляет студента в группу.
     *
     * @param idStudent Номер студента.
     * @param idGroup Номер группы.
     *
     * @throws UnsupportedOperationException Возникает при невозможности добавить студента в группу.
     *          Например студент уже имеется в группе.
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

        } else throw new UnsupportedOperationException();
    }

    /**
     * Метод создает группу.
     *
     * @param number Номер группы.
     * @param facult Название факультета.
     *
     * @throws UnsupportedOperationException Возникает при невозможности добавить группу.
     *          Например группа уже имеется.
     */
    public void addGroup(int number, String facult) {
        Group groupTemp = new Group(number, facult);
        int tempCount = 0;
        /** Поиск идентичной группы */
        for (int i = 0; i < group.size(); i++) {
            if (group.get(i).getNumber() == groupTemp.getNumber())
                if (group.get(i).getFacult().equals(groupTemp.getFacult()))
                    tempCount++;
        }
        /** Добавление группы */
        if (tempCount == 0) {
            group.add(groupTemp);
        } else throw new UnsupportedOperationException();
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
     *
     * @throws IndexOutOfBoundsException  Возникает, если группа не найдена.
     */
    public Group getGroup(int id) {
        for (Group g : group) {
            if (g.getNumber() == id) return g;
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Метод изменяет данные студента в общем списке и в группе, которой находится.
     *
     * @param id Номер студента.
     * @param name Uмя студента.
     * @param surname Фамилия студента.
     * @param patronymic Отчество студента.
     * @param date Дата зачисления студента.
     *
     * @throws UnsupportedOperationException Возникает при невозможности изменить студента.
     *         Например студент не найден.
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
        if (temp == 0) throw new UnsupportedOperationException();
    }

    /**
     * Метод изменяет данные группы.
     *
     * @param oldID Текущий номер группы.
     * @param newID Новый номер группы.
     * @param facult Новое название факультета.
     *
     * @throws UnsupportedOperationException Возникает при невозможности изменить группу.
     *          Например группа не найдена.
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

        if (temp == 0) throw new UnsupportedOperationException();
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
        ArrayList<Student> stTemp = new ArrayList<Student>();

        if (find.contains("*")) {
            find = find.replace("*", ".");
        } else if (find.contains("?")) {
            find = find.replace("?", "[а-яА-ЯёЁa-zA-Z0-9-\\s]*");
        }

        Pattern p = Pattern.compile("^" + find.toLowerCase() + "$");
        Matcher m;
        for (Student st : students) {
            m = p.matcher(st.getName().toLowerCase());
            if (m.matches()) {
                stTemp.add(st);
            } else {
                m = p.matcher(st.getPatronymic().toLowerCase());
                if (m.matches()) {
                    stTemp.add(st);
                } else {
                    m = p.matcher(st.getSurname().toLowerCase());
                    if (m.matches()) {
                        stTemp.add(st);
                    }
                }
            }
        }

       return stTemp;
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
        ArrayList<Group> grTemp = new ArrayList<Group>();

        if (find.contains("*")) {
            find = find.replace("*", ".");
        } else if (find.contains("?")) {
            find = find.replace("?", "[а-яА-ЯёЁa-zA-Z0-9-\\s]*");
        }

        Pattern p = Pattern.compile("^" + find.toLowerCase() + "$");
        Matcher m;
        for (Group gr : group) {
            m = p.matcher(gr.getFacult().toLowerCase());
            if (m.matches()) {
                grTemp.add(gr);
            }
        }

        return grTemp;
    }
}
