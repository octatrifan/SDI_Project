package model;

import java.util.Objects;

/**
 * Movie class
 *
 * @author Ani
 */

public class GasStation extends BaseEntity<Integer> {
    private String gasStationName;
    private int gasolinePrice;
    private int dieselPrice;

    public GasStation(String Name, Integer GasolinePrice, Integer DieselPrice) {
        this.gasStationName = Name;
        this.gasolinePrice = GasolinePrice;
        this.dieselPrice = DieselPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GasStation that = (GasStation) o;
        return gasolinePrice == that.gasolinePrice && dieselPrice == that.dieselPrice && Objects.equals(gasStationName, that.gasStationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), getGasStationName(), getGasolinePrice(), getDieselPrice());
    }

    @Override
    public String toString() {
        return "GasStation{" +
                "gasStationId=" + this.getId() +
                ", gasStationName='" + gasStationName + '\'' +
                ", gasolinePrice=" + gasolinePrice +
                ", dieselPrice=" + dieselPrice +
                '}';
    }

    public String getGasStationName() {
        return gasStationName;
    }

    public void setGasStationName(String gasStationName) {
        this.gasStationName = gasStationName;
    }

    public int getGasolinePrice() {
        return gasolinePrice;
    }

    public void setGasolinePrice(int gasolinePrice) {
        this.gasolinePrice = gasolinePrice;
    }

    public int getDieselPrice() {
        return dieselPrice;
    }

    public void setDieselPrice(int dieselPrice) {
        this.dieselPrice = dieselPrice;
    }
}
