package model;

import java.util.Date;
import java.util.Objects;

/**
 * Movie class
 * @author Tudor
 */

public class Rental extends BaseEntity<Integer> {
    private Integer MovieID;
    private Integer ClientID;
    private Date rentDate;
    private Date deadlineDate;

    public Rental(Integer movieID, Integer clientID, Date rentDate, Date deadlineDate, boolean isRented) {
        MovieID = movieID;
        ClientID = clientID;
        this.rentDate = rentDate;
        this.deadlineDate = deadlineDate;
        this.isRented = isRented;
    }

    private boolean isRented;

    public Integer getMovieID() {
        return MovieID;
    }

    public void setMovieID(Integer movieID) {
        MovieID = movieID;
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

        if (!Objects.equals(MovieID, rental.MovieID)) return false;
        if (!Objects.equals(ClientID, rental.ClientID)) return false;
        if (!Objects.equals(rentDate, rental.rentDate)) return false;
        if (!Objects.equals(isRented, rental.isRented)) return false;
        return deadlineDate.equals(rental.deadlineDate);

    }

    @Override
    public int hashCode() {
        int result = MovieID.hashCode();
        result = 31 * result + ClientID.hashCode();
        result = 31 * result + rentDate.hashCode();
        result = 31 * result + deadlineDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "MovieID=" + MovieID +
                ", ClientID=" + ClientID +
                ", rentDate=" + rentDate +
                ", deadlineDate=" + deadlineDate +
                ", isRented=" + isRented +
                '}';
    }

}
