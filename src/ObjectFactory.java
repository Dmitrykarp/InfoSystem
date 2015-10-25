

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;
import java.util.GregorianCalendar;


@XmlRegistry
public class ObjectFactory {

    private final static QName _Model_QNAME = new QName("", "model");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }


    @XmlElementDecl(namespace = "", name = "root")
    public JAXBElement<Model> createMOdel(Model value) {
        return new JAXBElement<Model>(_Model_QNAME, Model.class, null, value);
    }

}
