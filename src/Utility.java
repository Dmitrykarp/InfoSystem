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

public class Utility {

    public void loadZIP(String path) {
        ZipInputStream in = null;
        try {
            in = new ZipInputStream(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            //TODO Чтото выводить
            e.printStackTrace();
        }
        String targetfile = "src\\xml\\test.xml";
        OutputStream out = null;
        try {
            out = new FileOutputStream(targetfile);
        } catch (FileNotFoundException e) {
            //TODO Чтото выводить
            e.printStackTrace();
        }
        byte[] buf = new byte[1024];
        int len;

        try {
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (IOException e) {
            //TODO тоже самое
            e.printStackTrace();
        } finally {
            if (out == null & in == null){

                try {
                    out.close();
                    in.close();
                } catch (IOException e) {
                    //TODO чтото
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveZIP (String path){
        byte[] buf = new byte[1024];
        String target = "src\\test.zip";

        ZipOutputStream out = null;
        FileInputStream in = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(target));
            in = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            //TODO Чтото
            e.printStackTrace();
        }


        try {
            out.putNextEntry(new ZipEntry(path));
        } catch (IOException e) {
            //TODO чтото
            e.printStackTrace();
        }
        int len;

        try {
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (IOException e) {
            //TODO чтото
            e.printStackTrace();
        } finally {
            try {
                out.closeEntry();
                in.close();
                out.close();
            } catch (IOException e) {
                //TODO чтото
                e.printStackTrace();
            }
        }
    }

    public Model loadXML(String path) throws JAXBException {
        Model model = new Model();
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

        marshaller.marshal(this, fos);
    }

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
