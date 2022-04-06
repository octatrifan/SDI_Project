package main.java.model;

import java.util.Date;
import java.util.Objects;

/**
 * Movie class
 * @author Ani
 */

public class Car extends BaseEntity<Integer> {
    private String brand;
    private String model;
    private int makeYear;

    public Car(String brand, String model, int makeYear) {
        this.brand = brand;
        this.model = model;
        this.makeYear = makeYear;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMakeYear() {
        return makeYear;
    }

    public void setMakeYear(int makeYear) {
        this.makeYear = makeYear;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (!Objects.equals(brand, car.brand)) return false;
        if (!Objects.equals(model, car.model)) return false;
        return makeYear == car.makeYear;

    }

    @Override
    public int hashCode() {
        int result = brand.hashCode();
        result = 31 * result + model.hashCode();
        result = 31 * result + makeYear;
        return result;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", releaseDate=" + makeYear +
                '}';
    }

}
