package repo.XMLRepo;

import model.Employee;
import model.Fueling;
import org.w3c.dom.Element;
import validator.IValidator;

import javax.xml.parsers.DocumentBuilderFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * XML Repo for Fueling Class
 *
 * @author Liviu.
 */

public class FuelingXMLRepo extends XMLFileRepo<Integer, Fueling> {
    public FuelingXMLRepo(IValidator<Fueling> validator, String filename) {
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
     * Transforms Element into Fueling instance
     *
     * @param node - the element
     * @return Fueling
     */
    @Override
    Fueling createObject(Element node) {
        int id = Integer.parseInt(getTextFromTagName(node, "id"));
        int carID = Integer.parseInt(getTextFromTagName(node, "car_id"));
        int gasStationID = Integer.parseInt(getTextFromTagName(node, "gas_station_id"));
        String dateString = getTextFromTagName(node, "date");

        Date fuelDate;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        try {
            fuelDate = formatter.parse(dateString);
            Fueling fueling = new Fueling(carID, gasStationID, fuelDate);
            fueling.setId(id);
            return fueling;
        } catch (ParseException ex) {
            Fueling fueling = new Fueling(carID, gasStationID, null);
            fueling.setId(id);
            return fueling;
        }
    }

    /**
     * Transforms Fueling instance into Element
     *
     * @param fueling - the instance
     * @return Element
     */
    @Override
    Element ElementFromObject(Fueling fueling) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Element element = document.createElement("fueling");
        appendChildWithText(document, element, "id", fueling.getId().toString());
        appendChildWithText(document, element, "car_id", fueling.getCarID().toString());
        appendChildWithText(document, element, "gas_station_id", fueling.getGasStationID().toString());
        appendChildWithText(document, element, "date", formatter.format(fueling.getDate()));
        return element;
    }
}
