package repo.XMLRepo;

import model.Client;
import model.Employee;
import org.w3c.dom.Element;
import validator.IValidator;

import javax.xml.parsers.DocumentBuilderFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * XML Repo for Employee Class
 *
 * @author Octa.
 */


public class EmployeeXMLRepo extends XMLFileRepo<Integer, Employee> {

    public EmployeeXMLRepo(IValidator<Employee> validator, String filename) {
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
     * Transforms Element into Employee instance
     *
     * @param node - the element
     * @return Client
     */
    @Override
    Employee createObject(Element node) {
        int id = Integer.parseInt(getTextFromTagName(node, "id"));
        String firstname = getTextFromTagName(node, "firstname");
        String lastname = getTextFromTagName(node, "lastname");
        String birthdateString = getTextFromTagName(node, "birthdate");
        String email = getTextFromTagName(node, "email");
        int salary = Integer.parseInt(getTextFromTagName(node, "salary"));

        Date birthdate;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        try {
            birthdate = formatter.parse(birthdateString);
            Employee e = new Employee(firstname, lastname, birthdate, email, salary);
            e.setId(id);
            return e;
        } catch (ParseException ex) {
            Employee e = new Employee(firstname, lastname, null, email, salary);
            e.setId(id);
            return e;
        }

    }

    /**
     * Transforms Employee instance into Element
     *
     * @param obj - the instance
     * @return Element
     */
    @Override
    Element ElementFromObject(Employee obj) {

        Element element = document.createElement("employee");
        appendChildWithText(document, element, "id", obj.getId().toString());
        appendChildWithText(document, element, "firstname", obj.getFirstName());
        appendChildWithText(document, element, "lastname", obj.getLastName());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        appendChildWithText(document, element, "birthdate", formatter.format(obj.getBirthDate()));
        appendChildWithText(document, element, "email", obj.getEmail());
        appendChildWithText(document, element, "salary", Integer.toString(obj.getSalary().intValue()));
        return element;
    }
}