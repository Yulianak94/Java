package com.example.vaadapp.Models;

public class Payments {
    private String month,apartmentId,year;
    private int amount;

    public Payments(String month, int amount, String apatmentId, String year) {
        this.month = month;
        this.amount = amount;
        this.apartmentId = apatmentId;
        this.year = year;

    }

    public Payments() {
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "month: " + month + '\n' +
                "year: " + year + '\n' +
                "amount: " + amount;
    }
}
