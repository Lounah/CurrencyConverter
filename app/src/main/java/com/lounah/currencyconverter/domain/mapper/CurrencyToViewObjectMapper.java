package com.lounah.currencyconverter.domain.mapper;

import com.lounah.currencyconverter.core.Mapper;
import com.lounah.currencyconverter.domain.model.Currency;
import com.lounah.currencyconverter.presentation.viewobj.CurrencyViewObject;

import java.util.ArrayList;
import java.util.List;

public class CurrencyToViewObjectMapper implements Mapper<Currency, CurrencyViewObject> {

    @Override
    public CurrencyViewObject map(final Currency currency) {
        return new CurrencyViewObject(currency.getNumCode(), currency.getName(), currency.getCharCode());
    }

    @Override
    public List<CurrencyViewObject> map(final List<Currency> currencies) {
        List<CurrencyViewObject> result = new ArrayList<>();
        for (Currency currency : currencies) {
            result.add(map(currency));
        }
        return result;
    }
}
