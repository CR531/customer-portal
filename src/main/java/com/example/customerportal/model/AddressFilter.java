package com.example.customerportal.model;

public enum AddressFilter {
    Delhi("Delhi"),
    Noida("Noida"),
    Banglore("Banglore");

    private String str;

    AddressFilter(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
