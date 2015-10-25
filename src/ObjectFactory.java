
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRegistry;


@XmlRegistry
public class ObjectFactory {



    public ObjectFactory() {
    }


    public Model createModel() throws JAXBException {
        return new Model();
    }


    public Group createModelGroups() {
        return new Group();
    }

    public Student createModelGroupsStudents() {
        return new Student();
    }

}
