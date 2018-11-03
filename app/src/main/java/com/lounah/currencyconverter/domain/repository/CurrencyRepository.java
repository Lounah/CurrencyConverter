package com.lounah.currencyconverter.domain.repository;

import com.lounah.currencyconverter.domain.model.Currency;

import java.util.List;

public interface CurrencyRepository {
    List<Currency> getAvailableCurrencies();

    Currency getCurrencyByCode(final int code);
}
