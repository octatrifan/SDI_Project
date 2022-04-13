package model;

import java.util.Date;
import java.util.Objects;

public class Fueling extends BaseEntity<Integer> {
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public GasStation getGasStation() {
        return gasStation;
    }

    public void setGasStation(GasStation gasStation) {
        this.gasStation = gasStation;
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
        return Objects.equals(car, fueling.car) && Objects.equals(gasStation, fueling.gasStation) && Objects.equals(date, fueling.date);
    }

    @Override
    public String toString() {
        return "Fueling{" +
                "car=" + car +
                ", gasStation=" + gasStation +
                ", date=" + date +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(car, gasStation, date);
    }

    private Car car;
    private GasStation gasStation;
    private Date date;

    public Fueling(Car car1, GasStation gasStation1, Date date1) {
        this.car = car1;
        this.gasStation = gasStation1;
        this.date = date1;
    }


}