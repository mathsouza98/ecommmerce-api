package com.unesp.ecommerce.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Address {

    String street;
    String number;
    String neighborhood;
    String zip;
    String city;
    String fedUnit;

    public Address(String number, String neighborhood, String zip, String city, String fedUnit) {
        this.number = number;
        this.neighborhood = neighborhood;
        this.zip = zip;
        this.city = city;
        this.fedUnit = fedUnit;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFedUnit() {
        return fedUnit;
    }

    public void setFedUnit(String fedUnit) {
        this.fedUnit = fedUnit;
    }
}
