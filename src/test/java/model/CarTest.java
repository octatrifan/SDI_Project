package model;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void getBrand() {
        var c1 = new Car("Audi", "A3", 2020);
        assert (Objects.equals(c1.getBrand(), "Audi"));
    }

    @Test
    void getModel() {
        var c1 = new Car("Audi", "A3", 2020);
        assert (Objects.equals(c1.getModel(), "A3"));
    }

    @Test
    void setModel() {
        var c1 = new Car("Audi", "A3", 2020);
        c1.setModel("A4");
        assert (Objects.equals(c1.getModel(), "A4"));
    }

    @Test
    void getMakeYear() {
        var c1 = new Car("Audi", "A3", 2020);
        assert (Objects.equals(c1.getMakeYear(), 2020));
    }

    @Test
    void setMakeYear() {
        var c1 = new Car("Audi", "A3", 2020);
        c1.setMakeYear(2021);
        assert (Objects.equals(c1.getMakeYear(), 2021));
    }

    @Test
    void testEquals() {
        var c1 = new Car("Audi", "A3", 2020);
        var c2 = new Car("Audi", "A3", 2020);
        assert (c1.equals(c2));
    }

    @Test
    void testToString() {
        var c1 = new Car("Audi", "A3", 2020);
        System.out.println(c1.toString());
        assert(Objects.equals(c1.toString(), "Car{ID=null',brand='Audi', model='A3', releaseDate=2020}"));
    }
}