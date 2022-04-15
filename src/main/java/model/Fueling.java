package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Fueling class
 * @author Filip
 */

public class Fueling extends BaseEntity<Integer> {
    private Integer CarID;
    private Integer GasStationID;
    private Date date;

    public Fueling(Integer carID, Integer gasStationID, Date date)
    {
        CarID = carID;
        GasStationID = gasStationID;
        this.date = date;
    }

    public Integer getCarID()
    {
        return CarID;
    }

    public void setCarID(Integer carID)
    {
        this.CarID = carID;
    }

    public Integer getGasStationID()
    {
        return GasStationID;
    }

    public void setGasStationID(Integer gasStationID)
    {
        GasStationID = gasStationID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fueling fueling = (Fueling) o;
        return Objects.equals(CarID, fueling.CarID) && Objects.equals(GasStationID, fueling.GasStationID) && Objects.equals(date, fueling.date);
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        return "Fueling{" +
                "carID=" + CarID +
                ", gasStation=" + GasStationID +
                ", date=" + formatter.format(date) +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(CarID, GasStationID, date);
    }
}