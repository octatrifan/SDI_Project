package repo.XMLRepo;

import model.Fueling;
import model.Rental;
import org.w3c.dom.Element;
import validator.IValidator;

import javax.xml.parsers.DocumentBuilderFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * XML Repo for RentalFirm Class
 *
 * @author Liviu.
 */

public class RentalXMLRepo extends XMLFileRepo<Integer, Rental> {
    public RentalXMLRepo(IValidator<Rental> validator, String filename) {
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
    //     private Integer RentalFirmID;
    //    private Integer CarID;
    //    private Integer ClientID;
    //    private Date rentDate;
    //    private Date deadlineDate;
    //    private boolean isRented;

    /**
     * Transforms Element into Rental instance
     *
     * @param node - the element
     * @return Rental
     */
    @Override
    Rental createObject(Element node) {
        int id = Integer.parseInt(getTextFromTagName(node, "id"));
        int rentalFirmID = Integer.parseInt(getTextFromTagName(node, "rental_firm_id"));
        int carID = Integer.parseInt(getTextFromTagName(node, "car_id"));
        int clientID = Integer.parseInt(getTextFromTagName(node, "client_id"));
        String rentDateString = getTextFromTagName(node, "rent_date");
        String deadlineDateString = getTextFromTagName(node, "deadline_date");
        boolean isRented = Boolean.parseBoolean(getTextFromTagName(node, "is_rented"));

        Date rentDate, deadlineDate;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        try {
            rentDate = formatter.parse(rentDateString);
            deadlineDate = formatter.parse(deadlineDateString);
            Rental newRental = new Rental(carID, clientID, rentalFirmID, rentDate, deadlineDate, isRented);
            newRental.setId(id);
            return newRental;
        } catch (ParseException ex) {
            Rental newRental = new Rental(carID, clientID, rentalFirmID, null, null, isRented);
            newRental.setId(id);
            return newRental;
        }
    }

    /**
     * Transforms Rental instance into Element
     *
     * @param rental - the instance
     * @return Element
     */
    @Override
    Element ElementFromObject(Rental rental) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Element element = document.createElement("rental");
        appendChildWithText(document, element, "id", rental.getId().toString());
        appendChildWithText(document, element, "rental_firm_id", rental.getRentalFirmID().toString());
        appendChildWithText(document, element, "car_id", rental.getCarID().toString());
        appendChildWithText(document, element, "client_id", rental.getClientID().toString());
        appendChildWithText(document, element, "rent_date", formatter.format(rental.getRentDate()));
        appendChildWithText(document, element, "deadline_date", formatter.format(rental.getDeadlineDate()));
        appendChildWithText(document, element, "is_rented", String.valueOf(rental.isRented()));

        return element;
    }
}
