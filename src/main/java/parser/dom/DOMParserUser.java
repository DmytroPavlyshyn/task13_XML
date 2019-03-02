package parser.dom;

import model.Bank;
import model.BankComparator;
import org.w3c.dom.Document;
import parser.XMLValidator;

import java.io.File;
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
        List<Bank> banks = DOMParserUser.getBankList(new File("banksXML.xml"),new File("banksXSD.xsd"));
        banks.stream().sorted(new BankComparator()).forEach(System.out::println);
    }
}
