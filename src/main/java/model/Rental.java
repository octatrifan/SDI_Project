package model;

import java.util.Date;
import java.util.Objects;

/**
 * Rental class
 * @author Octa
 */

public class Rental extends BaseEntity<Integer> {
    private Integer CarID;
    private Integer ClientID;
    private String RentalFirm;
    private Date rentDate;
    private Date deadlineDate;


    public Rental(Integer carID, Integer clientID, String rentalFirm, Date rentDate, Date deadlineDate, boolean isRented) {
        CarID = carID;
        ClientID = clientID;
        RentalFirm = rentalFirm;
        this.rentDate = rentDate;
        this.deadlineDate = deadlineDate;
        this.isRented = isRented;

    }

    private boolean isRented;

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

    public String getRentalFirm() {return RentalFirm;}

    public void setRentalFirm(String rentalFirm) {RentalFirm = rentalFirm;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rental rental = (Rental) o;

        if (!Objects.equals(CarID, rental.CarID)) return false;
        if (!Objects.equals(RentalFirm, rental.RentalFirm)) return false;
        if (!Objects.equals(ClientID, rental.ClientID)) return false;
        if (!Objects.equals(rentDate, rental.rentDate)) return false;
        if (!Objects.equals(isRented, rental.isRented)) return false;
        return deadlineDate.equals(rental.deadlineDate);

    }

    @Override
    public int hashCode() {
        int result = CarID.hashCode();
        result = 31 * result + ClientID.hashCode();
        result = 31 * result + RentalFirm.hashCode();
        result = 31 * result + rentDate.hashCode();
        result = 31 * result + deadlineDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "CarID=" + CarID +
                ", ClientID=" + ClientID +
                ", RentalFirm=" + RentalFirm +
                ", rentDate=" + rentDate +
                ", deadlineDate=" + deadlineDate +
                ", isRented=" + isRented +
                '}';
    }

}
