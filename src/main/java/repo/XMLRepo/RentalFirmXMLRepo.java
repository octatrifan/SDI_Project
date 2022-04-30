package repo.XMLRepo;

import model.RentalFirm;
import org.w3c.dom.Element;
import validator.IValidator;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;

/**
 * XML Repo for RentalFirm Class
 *
 * @author Liviu.
 */

public class RentalFirmXMLRepo extends XMLFileRepo<Integer, RentalFirm> {
    public RentalFirmXMLRepo(IValidator<RentalFirm> validator, String filename) {
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
     * Transforms Element into RentalFirm instance
     *
     * @param node - the element
     * @return RentalFirm
     */
    @Override
    RentalFirm createObject(Element node) {
        int id = Integer.parseInt(getTextFromTagName(node, "id"));
        String rentalFirmName = getTextFromTagName(node, "rental_firm_name");
        String address = getTextFromTagName(node, "address");
        int availableCars = Integer.parseInt(getTextFromTagName(node, "available_cars"));

        RentalFirm newFirm = new RentalFirm(rentalFirmName, address, availableCars);
        newFirm.setId(id);
        return newFirm;
    }

    /**
     * Transforms RentalFirm instance into Element
     *
     * @param rentalFirm - the instance
     * @return Element
     */
    @Override
    Element ElementFromObject(RentalFirm rentalFirm) {
        Element element = document.createElement("rental_firm");
        appendChildWithText(document, element, "id", rentalFirm.getId().toString());
        appendChildWithText(document, element, "rental_firm_name", rentalFirm.getRentalFirmName());
        appendChildWithText(document, element, "address", rentalFirm.getAddress());
        appendChildWithText(document, element, "available_cars", String.valueOf(rentalFirm.getAvailableCars()));

        return element;
    }
}
