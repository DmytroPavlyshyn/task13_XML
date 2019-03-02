package parser;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class XMLValidator {
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

    public static boolean validate(Schema schema, File xml)  {
        try {
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
            return true;
        }catch (Exception e){
            return false;
        }
    }
}