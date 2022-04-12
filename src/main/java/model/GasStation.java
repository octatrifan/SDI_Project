package model;

import java.util.Objects;

/**
 * Movie class
 * @author Ani
 */

public class GasStation extends BaseEntity<Integer> {

    private int gasStationId;
    private String gasStationName;
    private int gasolinePrice;
    private int dieselPrice;

    public GasStation(Integer GasStationID, String Name, Integer GasolinePrice, Integer DieselPrice) {
        this.gasStationId = GasStationID;
        this.gasStationName = Name;
        this.gasolinePrice = GasolinePrice;
        this.dieselPrice = DieselPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GasStation)) return false;
        GasStation that = (GasStation) o;
        return getGasStationId() == that.getGasStationId() && getGasolinePrice() == that.getGasolinePrice() && getDieselPrice() == that.getDieselPrice() && Objects.equals(getGasStationName(), that.getGasStationName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGasStationId(), getGasStationName(), getGasolinePrice(), getDieselPrice());
    }

    @Override
    public String toString() {
        return "GasStation{" +
                "gasStationId=" + gasStationId +
                ", gasStationName='" + gasStationName + '\'' +
                ", gasolinePrice=" + gasolinePrice +
                ", dieselPrice=" + dieselPrice +
                '}';
    }

    public int getGasStationId() {
        return gasStationId;
    }

    public void setGasStationId(int gasStationId) {
        this.gasStationId = gasStationId;
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
