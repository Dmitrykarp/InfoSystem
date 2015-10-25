

/*
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    @XmlElementDecl(namespace = "", name = "group")
    public JAXBElement<Group> createGroup(Group value) {
        return new JAXBElement<Group>(new QName("group"), Group.class, value);
    }

    @XmlElementDecl( name = "facult")
    public JAXBElement<String> createFacult(String value) {
        return new JAXBElement<String>(new QName("facult"), String.class, value);
    }
}
*/