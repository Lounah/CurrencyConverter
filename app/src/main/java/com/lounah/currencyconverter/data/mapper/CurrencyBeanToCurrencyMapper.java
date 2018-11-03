package com.lounah.currencyconverter.data.mapper;

import com.lounah.currencyconverter.core.Mapper;
import com.lounah.currencyconverter.data.entity.CurrencyBean;
import com.lounah.currencyconverter.domain.model.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyBeanToCurrencyMapper implements Mapper<CurrencyBean, Currency> {
    @Override
    public Currency map(final CurrencyBean what) {
        return new Currency(what.getNumCode(), what.getCharCode(), what.getName(), what.getValue());
    }

    @Override
    public List<Currency> map(final List<CurrencyBean> what) {
        List<Currency> result = new ArrayList<>();
        for (CurrencyBean currency : what) {
            result.add(map(currency));
        }
        return result;
    }
}
