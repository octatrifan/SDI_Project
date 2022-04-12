package model;

import java.util.Objects;

/**
 * Rental class
 * @author Liviu
 */

public class RentalFirm extends BaseEntity<Integer>
{
    private String rentalFirmName;
    private String address;
    private int availableCars;

    public RentalFirm(String rentalFirmName, String address, int availableCars)
    {
        this.rentalFirmName = rentalFirmName;
        this.address = address;
        this.availableCars = availableCars;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalFirm that = (RentalFirm) o;
        return availableCars == that.availableCars && Objects.equals(rentalFirmName, that.rentalFirmName) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(rentalFirmName, address, availableCars);
    }

    @Override
    public String toString()
    {
        return "RentalFirm{" +
                ", rentalFirmName='" + rentalFirmName + '\'' +
                ", address='" + address + '\'' +
                ", availableCars=" + availableCars +
                '}';
    }

    public String getRentalFirmName()
    {
        return rentalFirmName;
    }

    public void setRentalFirmName(String rentalFirmName)
    {
        this.rentalFirmName = rentalFirmName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public int getAvailableCars()
    {
        return availableCars;
    }

    public void setAvailableCars(int availableCars)
    {
        this.availableCars = availableCars;
    }
}
