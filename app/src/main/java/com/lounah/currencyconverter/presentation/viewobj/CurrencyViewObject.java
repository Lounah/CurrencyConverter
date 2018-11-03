package com.lounah.currencyconverter.presentation.viewobj;

public class CurrencyViewObject {

    private int code;
    private String name;
    private String codeName;

    public CurrencyViewObject(final int code, final String name, final String codeName) {
        this.code = code;
        this.name = name;
        this.codeName = codeName;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCodeName() {
        return codeName;
    }
}
