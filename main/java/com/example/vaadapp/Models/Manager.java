package com.example.vaadapp.Models;

public class Manager {

    private int seniority;
    private String firstName, lastName, _id, email,apartmentId,buildingId, identity;

    public Manager(int seniority, String firstName, String lastName, String _id, String email, String identity) {
        this.seniority = seniority;
        this.firstName = firstName;
        this.lastName = lastName;
        this._id = _id;
        this.email = email;
        this.identity=identity;
    }

    public Manager(){}

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String get_id() {
        return _id;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public String getBuildingId() {
        return buildingId;
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

    public int getSeniority() {
        return seniority;
    }

    public void setSeniority(int seniority) {
        this.seniority = seniority;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
               return "First Name: " + firstName + '\n' +
                       "Last Name: " + lastName + '\n' +
                        "Seniority: " + seniority + '\n' +
                "Email: " + email + '\n' +
                "Identity: " + identity;
    }
}
