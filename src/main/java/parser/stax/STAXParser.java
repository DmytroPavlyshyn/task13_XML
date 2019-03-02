package parser.stax;


import model.Bank;
import model.DepositType;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class STAXParser {

    private static List<Bank> banks = new ArrayList<>();
    private static Bank bank = null;
    private static EnumSet<DepositType> depositTypes = null;
    private static boolean bName = false;
    private static boolean bCountry = false;
    private static boolean bDepositTypes = false;
    private static boolean bDepositType = false;
    private static boolean bDepositor = false;
    private static boolean bAccountId = false;
    private static boolean bAmountOnDeposit = false;
    private static boolean bProfitability = false;
    private static boolean bTimeConstraints = false;

    public static List<Bank> parseFile(File xml) {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(xml));
            bank = new Bank();
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                switch (event.getEventType()) {

                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        if (qName.equalsIgnoreCase("bank")) {
                            bank.setBankId(Integer.parseInt(startElement.getAttributeByName(new QName("bank_id")).getValue()));
                        } else if (qName.equalsIgnoreCase("name")) {
                            bName = true;
                        } else if (qName.equalsIgnoreCase("country")) {
                            bCountry = true;
                        } else if (qName.equalsIgnoreCase("deposit_types")) {
                            bDepositTypes = true;
                        } else if (qName.equalsIgnoreCase("deposit_type")) {
                            bDepositType = true;
                        } else if (qName.equalsIgnoreCase("depositor")) {
                            bDepositor = true;
                        } else if (qName.equalsIgnoreCase("account_id")) {
                            bAccountId = true;
                        } else if (qName.equalsIgnoreCase("amount_on_deposit")) {
                            bAmountOnDeposit = true;
                        } else if (qName.equalsIgnoreCase("profitability")) {
                            bProfitability = true;
                        } else if (qName.equalsIgnoreCase("time_constraints")) {
                            bTimeConstraints = true;
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();

                        if (bName) {
                            bank.setName(characters.getData());
                            bName = false;
                        }
                        if (bCountry) {
                            bank.setCountry(characters.getData());
                            bCountry = false;
                        }
                        if (bDepositTypes) {
                            depositTypes = EnumSet.noneOf(DepositType.class);
                            bDepositTypes = false;
                        }
                        if (bDepositType) {
                            DepositType depositType = DepositType.valueOf(characters.getData().toUpperCase());
                            depositTypes.add(depositType);
                            bDepositType = false;
                        }
                        if (bDepositor) {
                            bank.setDepositor(characters.getData());
                            bDepositor = false;
                        }
                        if (bAccountId) {
                            bank.setAccountId(Integer.parseInt(characters.getData()));
                            bAccountId = false;
                        }
                        if (bAmountOnDeposit) {
                            bank.setAmountOnDeposit(Integer.parseInt(characters.getData()));
                            bAmountOnDeposit = false;
                        }
                        if (bProfitability) {
                            bank.setProfitability(Integer.parseInt(characters.getData()));
                            bProfitability = false;
                        }
                        if (bTimeConstraints) {
                            bank.setTimeConstraints(LocalDate.parse(characters.getData()));
                            bTimeConstraints = false;
                            bank.setDepositTypes(depositTypes);
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();

                        if (endElement.getName().getLocalPart().equalsIgnoreCase("bank")) {
                            banks.add(bank);
                            bank = new Bank();
                        }
                        break;
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return banks;
    }

    public static void main(String[] args) {
        System.out.println(STAXParser.parseFile(new File("banksXML.xml")));

    }
}