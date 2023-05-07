package model.customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Address {
    private String street;
    private String city;
    private String country;
    private int zipCode;

    public Address(String street, String city, String country, int zipCode) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
    }

    public Address(ResultSet in) {
        this.read(in);
    }

    private void read(ResultSet in) {
        try {
            this.street = in.getString("street");
            this.city = in.getString("city");
            this.country = in.getString("country");
            this.zipCode = in.getInt("zipcode");
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }

    public String toCSV() {
        return street + "," + city + "," + country + "," + zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
}
