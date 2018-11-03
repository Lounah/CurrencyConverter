package com.lounah.currencyconverter.data.datasource.remote.currencies;

import com.lounah.currencyconverter.data.entity.CurrencyBean;

import java.util.List;

public interface CurrenciesApi {

    String ENDPOINT_URL = "http://www.cbr.ru/scripts/XML_daily.asp";

    List<CurrencyBean> fetchCurrencies(final RequestCallback callback);

    CurrencyBean fetchCurrencyByCode(final int code, final RequestCallback callback);

    boolean cacheIsEmpty();
}
