package parser.sax;


import model.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class SAXHandler extends DefaultHandler {
    private List<Bank> beerList = new ArrayList<>();
    private Bank bank = null;
    private EnumSet<DepositType> depositTypes;

    private boolean bName = false;
    private boolean bCountry = false;
    private boolean bDepositTypes = false;
    private boolean bDepositType = false;
    private boolean bDepositor = false;
    private boolean bAccountId = false;
    private boolean bAmountOnDeposit = false;
    private boolean bProfitability = false;
    private boolean bTimeConstraints = false;

    public SAXHandler() {
        depositTypes = null;
    }

    public List<Bank> getBeerList() {
        return this.beerList;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("bank")) {
            String beerN = attributes.getValue("bank_id");
            bank = new Bank();
            bank.setBankId(Integer.parseInt(beerN));
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
    }

    public void endElement(String uri, String localName, String qName)   {
        if (qName.equalsIgnoreCase("bank")) {
            beerList.add(bank);
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (bName) {
            bank.setName(new String(ch, start, length));
            bName = false;
        } else if (bCountry) {
            bank.setCountry(new String(ch, start, length));
            bCountry = false;
        } else if (bDepositor) {
            bank.setDepositor(new String(ch, start, length));
            bDepositor = false;
        } else if (bAccountId) {
            bank.setAccountId(Integer.parseInt(new String(ch,start,length)));
            bAccountId = false;
        } else if (bAmountOnDeposit) {
            bank.setAmountOnDeposit(Integer.parseInt(new String(ch,start,length)));
            bAmountOnDeposit = false;
        } else if (bProfitability) {
            bank.setProfitability(Integer.parseInt(new String(ch,start,length)));
            bProfitability = false;
        }else if (bTimeConstraints) {
            bank.setTimeConstraints(LocalDate.parse(new String(ch,start,length)));
            bTimeConstraints = false;
        }else if (bDepositTypes) {
            depositTypes = EnumSet.noneOf(DepositType.class);
            bDepositTypes = false;
        } else if (bDepositType) {
            DepositType depositType = DepositType.valueOf(new String(ch, start, length).toUpperCase());
            depositTypes.add(depositType);
            bDepositType = false;
            bank.setDepositTypes(depositTypes);
        }
    }
}

