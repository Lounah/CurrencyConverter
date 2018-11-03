package com.lounah.currencyconverter.data.api;

import com.lounah.currencyconverter.data.datasource.remote.converter.XMLToBeanConverter;
import com.lounah.currencyconverter.data.datasource.remote.currencies.CurrenciesApiImpl;
import com.lounah.currencyconverter.data.datasource.remote.currencies.RequestCallback;
import com.lounah.currencyconverter.data.entity.CurrencyBean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class CurrencyApiTest {

    @Mock
    private XMLToBeanConverter<String, List<CurrencyBean>> xmlConverter;

    private CurrenciesApiImpl api;

    private RequestCallback<CurrencyBean> stubCallback = new RequestCallback<CurrencyBean>() {
        @Override
        public void onComplete(List<CurrencyBean> result) {

        }

        @Override
        public void onError() {

        }
    };

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        api = new CurrenciesApiImpl(xmlConverter);
    }

    @Test
    public void fetchesFromNet_whenCacheIsEmpty() {
        api.fetchCurrencies(stubCallback);

        try {
            Mockito.verify(xmlConverter).convert(Mockito.anyString());
        } catch (Exception e) {
        }

        Mockito.verifyNoMoreInteractions(xmlConverter);
    }

    @Test
    public void fetchesFromCache_ifCan() {
        List<CurrencyBean> cached = new ArrayList<>();
        cached.add(new CurrencyBean(0, "", 1, "", 1));
        api.setCachedCurrencies(cached);

        api.fetchCurrencies(stubCallback);

        Mockito.verifyZeroInteractions(xmlConverter);
    }

    @Test
    public void fetchesByCode_correctly() {
        List<CurrencyBean> cached = new ArrayList<>();
        cached.add(new CurrencyBean(0, "", 1, "", 1));
        api.setCachedCurrencies(cached);

        assertEquals(api.fetchCurrencyByCode(0, stubCallback).getNumCode(), 0);
    }
}
