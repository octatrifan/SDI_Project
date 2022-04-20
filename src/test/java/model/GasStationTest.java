package model;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class GasStationTest {

    @Test
    void testEquals() {
        var g1 = new GasStation("BP", 7, 8);
        var g2 = new GasStation("BP", 7, 8);
        var g3 = new GasStation("Rompetrol", 7, 8);
        var g4 = g1;
        assert(g1.equals(g2));
        assert(!g1.equals(g3));
        assert(g1.equals(g4));
    }

    @Test
    void testToString() {
        var g1 = new GasStation("BP", 7, 8);
//        System.out.println(g1.toString());
        assert(Objects.equals(g1.toString(), "GasStation{gasStationId=null, gasStationName='BP', gasolinePrice=7, dieselPrice=8}"));
    }

    @Test
    void getGasStationName() {
        var g1 = new GasStation("BP", 7, 8);
        assert(g1.getGasStationName().equals("BP"));
    }

    @Test
    void setGasStationName() {
        var g1 = new GasStation("BP", 7, 8);
        g1.setGasStationName("OMV");
        assert(g1.getGasStationName().equals("OMV"));
    }

    @Test
    void getGasolinePrice() {
        var g1 = new GasStation("BP", 7, 8);
        assert(g1.getGasolinePrice() == 7);
    }

    @Test
    void setGasolinePrice() {
        var g1 = new GasStation("BP", 7, 8);
        g1.setGasolinePrice(10);
        assert(g1.getGasolinePrice() == 10);
    }

    @Test
    void getDieselPrice() {
        var g1 = new GasStation("BP", 7, 8);
        assert(g1.getGasolinePrice() == 7);
    }

    @Test
    void setDieselPrice() {
        var g1 = new GasStation("BP", 7, 8);
        g1.setGasolinePrice(10);
        assert(g1.getGasolinePrice() == 10);
    }
}