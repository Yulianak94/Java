package com.example.vaadapp.Models;



public class Apartment {

    private int apartmentNumber;
    private String _id;
    public Apartment(int apartmentNumber, String _id){
        this.apartmentNumber=apartmentNumber;
        this._id = _id;
    }

    public Apartment(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public Apartment(){}

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public String get_id() {
        return _id;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    @Override
    public String toString() {
        return
                "Apartment Number: " + apartmentNumber;

    }
}