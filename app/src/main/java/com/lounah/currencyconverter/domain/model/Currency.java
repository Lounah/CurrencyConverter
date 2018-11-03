package com.lounah.currencyconverter.domain.model;

public class Currency {

    private int numCode;
    private String charCode;
    private String name;
    private double value;

    public Currency(final int numCode, final String charCode, final String name, final double value) {
        this.charCode = charCode;
        this.name = name;
        this.value = value;
        this.numCode = numCode;
    }

    public int getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

}
