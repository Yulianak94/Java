package com.example.vaadapp.Models;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Building {
    private ArrayList<Apartment> arrApartment;
    private int buildingNumber;
    private String manager;
    private int maxApartments;
    private String _id;
    private String address,entry;


    public Building(int buildingNumber, String manager, int maxApartments,String entry ,String address, String _id) {
        this.buildingNumber = buildingNumber;
        this.manager = manager;
        this.maxApartments = maxApartments;
        this._id = _id;
        this.address = address;
        this.entry = entry;
    }
    public Building(int buildingNumber, String manager, int maxApartments,String entry ,String address) {
        this.buildingNumber = buildingNumber;
        this.manager = manager;
        this.maxApartments = maxApartments;
        this.address = address;
        this.entry = entry;
    }


    public Building() {
    }



    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public int getMaxApartments() {
        return maxApartments;
    }

    public void setMaxApartments(int maxApartments) {
        this.maxApartments = maxApartments;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }
    //methods

    @Override
    public String toString() {
        return "Address: " + this.getAddress() + ", Building Number: " + this.getBuildingNumber();
    }
}
