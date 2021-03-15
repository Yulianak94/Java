package com.example.vaadapp.Models;

public class User {
    private String firstName;
    private String lastName;
    private String _id;
    private String email;
    private String apartmentId;
    private String buildingId;

    public User(String firstName, String lastName, String _id, String email, String apartmentId, String buildingId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this._id = _id;
        this.email = email;
        this.apartmentId = apartmentId;
        this.buildingId = buildingId;
    }
    public User(){}

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public String toString() {
        return "First Name: " + firstName + '\n' +
                "Last Name:'" + lastName + '\n' +
                "Email:'" + email;
    }
}
