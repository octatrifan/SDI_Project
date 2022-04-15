package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Car class
 * @author Octa
 */

public class Rental extends BaseEntity<Integer> {
    private Integer RentalFirmID;
    private Integer CarID;
    private Integer ClientID;
    private Date rentDate;
    private Date deadlineDate;
    private boolean isRented;

    public Rental(Integer carID, Integer clientID, Integer rentalFirmID, Date rentDate, Date deadlineDate, boolean isRented) {
        CarID = carID;
        ClientID = clientID;
        RentalFirmID = rentalFirmID;
        this.rentDate = rentDate;
        this.deadlineDate = deadlineDate;
        this.isRented = isRented;
    }

    public Integer getRentalFirmID() {
        return RentalFirmID;
    }

    public void setRentalFirmID(Integer rentalFirmID) {
        RentalFirmID = rentalFirmID;
    }

    public Integer getCarID() {
        return CarID;
    }

    public void setCarID(Integer carID) {
        CarID = carID;
    }

    public Integer getClientID() {
        return ClientID;
    }

    public void setClientID(Integer clientID) {
        ClientID = clientID;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rental rental = (Rental) o;

        if (!Objects.equals(CarID, rental.CarID)) return false;
        if (!Objects.equals(RentalFirmID, rental.RentalFirmID)) return false;
        if (!Objects.equals(ClientID, rental.ClientID)) return false;
        if (!Objects.equals(rentDate, rental.rentDate)) return false;
        if (!Objects.equals(isRented, rental.isRented)) return false;
        return deadlineDate.equals(rental.deadlineDate);

    }

    @Override
    public int hashCode() {
        int result = CarID.hashCode();
        result = 31 * result + ClientID.hashCode();
        result = 31 * result + RentalFirmID.hashCode();
        result = 31 * result + rentDate.hashCode();
        result = 31 * result + deadlineDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        return "Rental{" +
                "CarID=" + CarID +
                ", ClientID=" + ClientID +
                ", RentalFirmID=" + RentalFirmID +
                ", rentDate=" + formatter.format(rentDate) +
                ", deadlineDate=" + formatter.format(deadlineDate) +
                ", isRented=" + isRented +
                '}';
    }

}
