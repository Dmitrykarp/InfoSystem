import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Утилитный класс для работы с файловой системой.
 * Позволяет работать с ZIP архивами и XML файлами.
 *
 * @author Karpenko Dmitry
 */
public class Utility {

    /**
     * Метод выгружает XML файл из ZIP-архива.
     *
     * @param path Путь к ZIP-архиву.
     */
    public void loadZIP(String path)  {
        ZipInputStream in = null;
        OutputStream out = null;
        try{
            in = new ZipInputStream(new FileInputStream(path));
            //Берем первый элемент из архива.
            ZipEntry entry = in.getNextEntry();
            String targetfile = "src\\xml\\test.xml";
            out = new FileOutputStream(targetfile);
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *Метод сжимает XML файл в ZIP-архив.
     *
     * @param path Путь до XML файла.
     */
    public void saveZIP (String path){
        byte[] buf = new byte[1024];
        String target = "src\\test.zip";

        ZipOutputStream out = null;
        FileInputStream in = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(target));
            in = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            out.putNextEntry(new ZipEntry(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int len;

        try {
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.closeEntry();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод загружает данные из XML файла.
     *
     * @param path Путь к файлу.
     *
     * @return Экземпляр Model с обновленными данными.
     *
     * @throws JAXBException Возникает при неверном указании источника данных или его отсутствии.
     */
    public Model loadXML(String path) throws JAXBException {
        Model model = new Model();
        JAXBContext jaxbCtx = JAXBContext.newInstance(model.getClass());
        Unmarshaller um = jaxbCtx.createUnmarshaller();
        model = (Model) um.unmarshal(new File(path));
        ArrayList<Student> studentsInGroup = new ArrayList<Student>();

        /** Переносим студентов из конкретных групп в общую базу */
        for(Group group: model.getGroups()){
            studentsInGroup.addAll(group.getStudents());
        }
        for (Student student: studentsInGroup){
            model.addStudent(student.getId(),
                    student.getName(),
                    student.getSurname(),
                    student.getPatronymic(),
                    student.getDate());
        }

        return model;
    }

    /**
     * Метод сохраняет данные в XML файл.
     *
     * @param path Путь до конечного XML файла.
     *
     * @param model Экзмепляр Model, которую необходимо сохранить.
     *
     * @throws JAXBException Возникает при неверном указании источника данных или его отсутствии.
     */
    public void saveXML(String path, Model model) throws JAXBException {
        JAXBContext jaxbCtx = JAXBContext.newInstance(model.getClass());
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

        marshaller.marshal(model, fos);
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
                st.setId(model.getStudents().size() + 1);
        /** Добавляем группы и студентов в модель */
        for(Group group: mTemp.getGroups()){
            model.addGroup(group);
            for(Student student: group.getStudents()){
                String name = student.getName();
                String patronymic = student.getPatronymic();
                String surname = student.getSurname();
                Date date = student.getDate();
                model.addStudent(name, surname, patronymic, date);
            }
        }

        return model;
    }

    /**
     * Метод переносит студентов из группы в общий список студентов.
     *
     * @param model Экземпляр Model для обновления списка студентов.
     *
     * @return Обновленный экземпляр Model.
     */
    public Model addStudentInStudentList(Model model) {
        ArrayList<Student> grSt = new ArrayList<Student>();

        for (int i = 0; i < model.getGroups().size(); i++) {
            grSt.addAll(model.getGroups().get(i).getStudents());
        }

        for (int i = 0; i < grSt.size(); i++) {
            model.addStudent(grSt.get(i).getName(),
                    grSt.get(i).getSurname(),
                    grSt.get(i).getPatronymic(),
                    grSt.get(i).getDate());
        }

        return model;
    }
}
