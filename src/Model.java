import java.util.ArrayList;


public class Model {
    private ArrayList<Student> students;
    private ArrayList<Group> groups;

    Model() {
        loadXML("test.xml");
    }

    public void loadXML(String path) {

    }

    public void saveXML(String path) {

    }

    public void addStudent(String n, String s, String p) {

    }

    public void delStudent(int n) {

    }

    public void modifyStudent(int i, String n, String s, String p) {

    }

    public void addGroup(int n, String f) {

    }

    public void delGroup(int n) {

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
