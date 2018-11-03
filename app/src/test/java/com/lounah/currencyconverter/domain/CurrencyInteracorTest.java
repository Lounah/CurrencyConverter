package com.lounah.currencyconverter.domain;

import com.lounah.currencyconverter.core.Mapper;
import com.lounah.currencyconverter.domain.interactor.CurrencyConverterInteractor;
import com.lounah.currencyconverter.domain.model.Currency;
import com.lounah.currencyconverter.domain.repository.CurrencyRepository;
import com.lounah.currencyconverter.presentation.viewobj.CurrencyViewObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class CurrencyInteracorTest {

    @Mock
    CurrencyRepository repository;

    @Mock
    Mapper<Currency, CurrencyViewObject> mapper;

    private CurrencyConverterInteractor interactor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        interactor = new CurrencyConverterInteractor(repository, mapper);
    }

    @Test
    public void getsAvailableCurrencies_correctly() {
        interactor.getAvailableCurrencies();

        Mockito.verify(repository).getAvailableCurrencies();
        Mockito.verifyNoMoreInteractions(repository);
        Mockito.verify(mapper).map(Mockito.anyList());
        Mockito.verifyNoMoreInteractions(mapper);
    }

    @Test
    public void convertsCurrency_correctly() {
        final Currency stubCurrencyFrom = new Currency(0, "RUB", "Российский рубль", 1);
        final Currency stubCurrencyTo = new Currency(1, "USD", "US Dollar", 65);
        Mockito.when(repository.getCurrencyByCode(0)).thenReturn(stubCurrencyFrom);
        Mockito.when(repository.getCurrencyByCode(1)).thenReturn(stubCurrencyTo);

        assertEquals(interactor.getExchangeRate(1, 0, 10), "0.15");
    }
}
