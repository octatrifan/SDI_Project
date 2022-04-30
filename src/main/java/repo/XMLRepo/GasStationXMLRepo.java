package repo.XMLRepo;

import model.GasStation;
import org.w3c.dom.Element;
import validator.IValidator;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;

/**
 * XML Repo for GasStation Class
 *
 * @author Liviu.
 */


public class GasStationXMLRepo extends XMLFileRepo<Integer, GasStation> {
    public GasStationXMLRepo(IValidator<GasStation> validator, String filename) {
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
     * Transforms Element into GasStation instance
     *
     * @param node - the element
     * @return GasStation
     */
    @Override
    GasStation createObject(Element node) {
        int id = Integer.parseInt(getTextFromTagName(node, "id"));
        String gasStationName = getTextFromTagName(node, "gas_station_name");
        int gasPrice = Integer.parseInt(getTextFromTagName(node, "gasoline_price"));
        int dieselPrice = Integer.parseInt(getTextFromTagName(node, "diesel_price"));

        GasStation gasStation = new GasStation(gasStationName, gasPrice, dieselPrice);
        gasStation.setId(id);
        return gasStation;
    }

    /**
     * Transforms GasStation instance into Element
     *
     * @param gasStation - the instance
     * @return Element
     */
    @Override
    Element ElementFromObject(GasStation gasStation) {
        Element element = document.createElement("gas_station");
        appendChildWithText(document, element, "id", gasStation.getId().toString());
        appendChildWithText(document, element, "gas_station_name", gasStation.getGasStationName());
        appendChildWithText(document, element, "gasoline_price", String.valueOf(gasStation.getGasolinePrice()));
        appendChildWithText(document, element, "diesel_price", String.valueOf(gasStation.getDieselPrice()));
        return element;
    }
}
