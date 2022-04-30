package repo.XMLRepo;

import model.Client;
import org.w3c.dom.Element;
import validator.IValidator;

import javax.xml.parsers.DocumentBuilderFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

/**
 * XML Repo for Client Class
 *
 * @author Octa.
 */


public class ClientXMLRepo extends XMLFileRepo<Integer, Client> {

    public ClientXMLRepo(IValidator<Client> validator, String filename) {
        this.validator = validator;
        this.fileName = filename;
        this.entities = new HashMap<>();
        try {
            this.document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(fileName);
            loadData();
        } catch (Exception e) {
        }
    }

    /**
     * Transforms Element into Client instance
     *
     * @param node - the element
     * @return Client
     */
    @Override
    Client createObject(Element node) {
        int id = Integer.parseInt(getTextFromTagName(node, "id"));
        String firstname = getTextFromTagName(node, "firstname");
        String lastname = getTextFromTagName(node, "lastname");
        String birthdateString = getTextFromTagName(node, "birthdate");
        String email = getTextFromTagName(node, "email");

        Date birthdate;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        try {
            birthdate = formatter.parse(birthdateString);
            Client c = new Client(firstname, lastname, birthdate, email);
            c.setId(id);
            return c;
        } catch (ParseException ex) {
            Client c = new Client(firstname, lastname, null, email);
            c.setId(id);
            return c;
        }

    }

    /**
     * Transforms Client instance into Element
     *
     * @param obj - the instance
     * @return Element
     */
    @Override
    Element ElementFromObject(Client obj) {

        Element element = document.createElement("client");
        appendChildWithText(document, element, "id", obj.getId().toString());
        appendChildWithText(document, element, "firstname", obj.getFirstName());
        appendChildWithText(document, element, "lastname", obj.getLastName());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        appendChildWithText(document, element, "birthdate", formatter.format(obj.getBirthDate()));
        appendChildWithText(document, element, "email", obj.getEmail());
        return element;
    }
}