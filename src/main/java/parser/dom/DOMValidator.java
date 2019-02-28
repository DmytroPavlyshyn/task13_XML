package parser.dom;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class DOMValidator {
    public static Schema createSchema(File xsd){
        Schema schema = null;
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);
            schema = factory.newSchema(xsd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schema;
    }

    public static void validate(Schema schema, Document xml) throws IOException, SAXException {
        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(xml));
    }


}
