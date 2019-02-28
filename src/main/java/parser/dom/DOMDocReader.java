package parser.dom;

import model.Bank;
import model.DepositType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class DOMDocReader {
    public List<Bank> readDoc(Document doc) {
        doc.getDocumentElement().normalize();
        List<Bank> banks = new ArrayList<>();

        NodeList nodeList = doc.getElementsByTagName("bank");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Bank bank = new Bank();
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                bank.setBankId(Integer.parseInt(element.getAttribute("bank_id")));
                bank.setName(element.getElementsByTagName("name").item(0).getTextContent());
                bank.setCountry(element.getElementsByTagName("country").item(0).getTextContent());

                bank.setDepositTypes(getDepositTypes(element.getElementsByTagName("deposit_types")));
                bank.setDepositor(element.getElementsByTagName("depositor").item(0).getTextContent());

                System.out.println(element.getElementsByTagName("account_id").item(0).getTextContent());
                bank.setAccountId(Integer.parseInt(element.getElementsByTagName("account_id").item(0).getTextContent()));
                bank.setAmountOnDeposit(Integer.parseInt(element.getElementsByTagName("amount_on_deposit").item(0).getTextContent()));
                bank.setProfitability(Integer.parseInt(element.getElementsByTagName("profitability").item(0).getTextContent()));
                bank.setTimeConstraints(LocalDate.parse(element.getElementsByTagName("time_constraints").item(0).getTextContent()));
                System.out.println(bank);
                banks.add(bank);
            }
        }
        return banks;
    }

    private EnumSet<DepositType> getDepositTypes(NodeList nodes) {
        EnumSet<DepositType> depositTypes = EnumSet.noneOf(DepositType.class);
        if (nodes.item(0).getNodeType() == Node.ELEMENT_NODE) {

            Element element = (Element) nodes.item(0);
            NodeList nodeList = element.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;
                    depositTypes.add(Arrays.stream(DepositType.values())
                            .filter((t) ->
                                    t.name().toLowerCase().matches(el.getTextContent()))
                            .findFirst().get());
                }
            }
        }

        return depositTypes;
    }


}
