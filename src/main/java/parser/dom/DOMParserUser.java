package parser.dom;

import model.Bank;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import parser.XMLValidator;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DOMParserUser {
    public static List<Bank> getBankList(File xml, File xsd){
        DOMDocCreator creator = new DOMDocCreator(xml);
        Document doc = creator.getDocument();

        if(!XMLValidator.validate(XMLValidator.createSchema(xsd),xml)){
            throw new RuntimeException("validation failed");
        }


        DOMDocReader reader = new DOMDocReader();

        return reader.readDoc(doc);
    }

    public static void main(String[] args) {
        System.out.println(DOMParserUser.getBankList(new File("banksXML.xml"),new File("banksXSD.xsd")));
    }
}
