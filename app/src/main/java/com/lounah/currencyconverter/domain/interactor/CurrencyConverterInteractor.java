package com.lounah.currencyconverter.domain.interactor;

import com.lounah.currencyconverter.core.Mapper;
import com.lounah.currencyconverter.domain.model.Currency;
import com.lounah.currencyconverter.domain.repository.CurrencyRepository;
import com.lounah.currencyconverter.presentation.viewobj.CurrencyViewObject;

import java.util.List;

public class CurrencyConverterInteractor {

    private CurrencyRepository repository;
    private Mapper<Currency, CurrencyViewObject> mapper;

    public CurrencyConverterInteractor(final CurrencyRepository repository,
                                       final Mapper<Currency, CurrencyViewObject> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CurrencyViewObject> getAvailableCurrencies() {
        return mapper.map(repository.getAvailableCurrencies());
    }

    public String getExchangeRate(int codeFrom, int codeTo, double valueToExchange) {
        Currency currencyFrom = repository.getCurrencyByCode(codeFrom);
        Currency currencyTo = repository.getCurrencyByCode(codeTo);

        double exchangeRate = currencyTo.getValue() / currencyFrom.getValue();

        return String.valueOf(Math.round(exchangeRate * valueToExchange * 100) / 100.0);
    }
}
