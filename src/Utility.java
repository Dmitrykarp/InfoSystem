import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
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
}
