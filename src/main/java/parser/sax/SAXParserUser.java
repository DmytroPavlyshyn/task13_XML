package parser.sax;

import model.Bank;
import org.xml.sax.SAXException;
import parser.XMLValidator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SAXParserUser {
    private static SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

    public static List<Bank> parseBeers(File xml, File xsd){
        List<Bank> beerList = new ArrayList<>();
        try {
            Schema schema = XMLValidator.createSchema(xsd);
            saxParserFactory.setSchema(XMLValidator.createSchema(xsd));

            if(!XMLValidator.validate(schema,xml)){
                throw new RuntimeException("Validation failed");
            }
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SAXHandler saxHandler = new SAXHandler();
            saxParser.parse(xml, saxHandler);

            beerList = saxHandler.getBeerList();
        }catch (SAXException | ParserConfigurationException | IOException ex){
            ex.printStackTrace();
        }

        return beerList;
    }

    public static void main(String[] args) {
        System.out.println(SAXParserUser.parseBeers(new File("banksXML.xml"), new File("banksXSD.xsd")));
    }
}
