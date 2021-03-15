package com.example.vaadapp.Models;

public class Casher {
    String month;
    long totalAmount;

    public Casher(String month, long totalAmount) {
        this.month = month;
        this.totalAmount = totalAmount;
    }
    public Casher(){}

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Month: " + month +  '\n' +
                "Total: " + totalAmount;
    }
}
