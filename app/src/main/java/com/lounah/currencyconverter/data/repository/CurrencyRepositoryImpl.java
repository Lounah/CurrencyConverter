package com.lounah.currencyconverter.data.repository;

import android.support.annotation.VisibleForTesting;

import com.lounah.currencyconverter.data.datasource.local.dao.currencies.CurrenciesDao;
import com.lounah.currencyconverter.data.datasource.remote.currencies.CurrenciesApi;
import com.lounah.currencyconverter.core.Mapper;
import com.lounah.currencyconverter.data.datasource.remote.currencies.RequestCallback;
import com.lounah.currencyconverter.data.entity.CurrencyBean;
import com.lounah.currencyconverter.domain.model.Currency;
import com.lounah.currencyconverter.domain.repository.CurrencyRepository;

import java.util.ArrayList;
import java.util.List;

public class CurrencyRepositoryImpl implements CurrencyRepository {

    private CurrenciesApi api;
    private CurrenciesDao dao;
    private Mapper<CurrencyBean, Currency> mapper;

    private List<Currency> cachedCurrencies = new ArrayList<>();

    public CurrencyRepositoryImpl(final CurrenciesApi api,
                                  final CurrenciesDao dao,
                                  final Mapper<CurrencyBean, Currency> mapper) {
        this.api = api;
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public List<Currency> getAvailableCurrencies() {
        if (!cachedCurrencies.isEmpty()) {
            return cachedCurrencies;
        } else {
            api.fetchCurrencies(new RequestCallback<CurrencyBean>() {
                @Override
                public void onComplete(List<CurrencyBean> result) {
                    dao.addAll(result);
                    setCachedCurrencies(mapper.map(result));
                }

                @Override
                public void onError() {
                    // TODO: handle error state
                }
            });
        }
        return mapper.map(dao.getAll());
    }

    @Override
    public Currency getCurrencyByCode(int code) {
        return mapper.map(dao.getByCode(code));
    }

    @VisibleForTesting
    public void setCachedCurrencies(List<Currency> currencies) {
        cachedCurrencies.clear();
        cachedCurrencies.addAll(currencies);
    }
}
