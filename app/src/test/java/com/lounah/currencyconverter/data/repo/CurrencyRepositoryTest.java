package com.lounah.currencyconverter.data.repo;

import com.lounah.currencyconverter.core.Mapper;
import com.lounah.currencyconverter.data.datasource.local.dao.currencies.CurrenciesDao;
import com.lounah.currencyconverter.data.datasource.remote.currencies.CurrenciesApi;
import com.lounah.currencyconverter.data.entity.CurrencyBean;
import com.lounah.currencyconverter.data.repository.CurrencyRepositoryImpl;
import com.lounah.currencyconverter.domain.model.Currency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class CurrencyRepositoryTest {

    @Mock
    private CurrenciesApi api;
    @Mock
    private CurrenciesDao dao;
    @Mock
    private Mapper<CurrencyBean, Currency> mapper;

    private CurrencyRepositoryImpl repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        repository = new CurrencyRepositoryImpl(api, dao, mapper);
    }

    @Test
    public void loadsAvailableCurrencies_fromApi_whenCacheIsEmpty() {
        repository.getAvailableCurrencies();

        Mockito.verify(api).fetchCurrencies(Mockito.any());
        Mockito.verify(mapper).map(Mockito.anyList());
        Mockito.verify(dao).getAll();
        Mockito.verifyNoMoreInteractions(api, dao, mapper, dao);
    }

    @Test
    public void loadsAvailableCurrencies_fromCache_ifCan() {
        List<Currency> cached = new ArrayList<>();
        cached.add(new Currency(1, "", "", 1));
        repository.setCachedCurrencies(cached);
        repository.getAvailableCurrencies();

        Mockito.verifyZeroInteractions(api, dao, mapper);
    }

    @Test
    public void getsCurrencyByCode_correctly() {
        final Currency stubMapped = new Currency(0, "", "", 1);
        final CurrencyBean stub = new CurrencyBean(0, "", 1, "", 1);
        Mockito.when(dao.getByCode(0)).thenReturn(stub);
        Mockito.when(mapper.map(stub)).thenReturn(stubMapped);

        repository.getCurrencyByCode(0);

        Mockito.verify(mapper).map(stub);
        Mockito.verify(dao).getByCode(0);
        Mockito.verifyNoMoreInteractions(mapper, dao);
    }
}
