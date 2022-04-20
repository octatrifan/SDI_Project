package repo.XMLRepo;

import model.Car;
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
 * XML Repo for Car Class
 *
 * @author Octa.
 */

public class CarXMLRepo extends XMLFileRepo<Integer, Car> {

    public CarXMLRepo(IValidator<Car> validator, String filename) {
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
     * Transforms Element into Car instance
     *
     * @param node - the element
     * @return Car
     */
    @Override
    Car createObject(Element node) {
        int id = Integer.parseInt(getTextFromTagName(node, "id"));
        String brand = getTextFromTagName(node, "brand");
        String model = getTextFromTagName(node, "model");
        int makeyear = Integer.parseInt(getTextFromTagName(node, "makeyear"));
        Car c = new Car(brand, model, makeyear);
        c.setId(id);
        return c;

    }

    /**
     * Transforms Car instance into Element
     *
     * @param obj - the instance
     * @return Element
     */
    @Override
    Element ElementFromObject(Car obj) {

        Element element = document.createElement("car");
        appendChildWithText(document, element, "id", obj.getId().toString());
        appendChildWithText(document, element, "brand", obj.getBrand());
        appendChildWithText(document, element, "model", obj.getModel());
        appendChildWithText(document, element, "makeyear", Integer.toString(obj.getMakeYear()));
        return element;
    }
}