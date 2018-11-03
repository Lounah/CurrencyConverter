package com.lounah.currencyconverter.presentation.converter;

import com.lounah.currencyconverter.presentation.common.BasePresenter;
import com.lounah.currencyconverter.presentation.common.BaseView;
import com.lounah.currencyconverter.presentation.viewobj.CurrencyViewObject;

import java.util.List;

public interface ConvertCurrencyContract {
    interface View extends BaseView<Presenter> {
        void showLoading();

        void hideLoading();

        void showError();

        void showConvertedCurrency(String value);

        void showAvailableCurrenciesList(List<CurrencyViewObject> currencies);
    }

    interface Presenter extends BasePresenter {
        void fetchExchangeRate(int currencyCodeFrom, int currencyCodeTo, double valueToExchange);

        List<CurrencyViewObject> fetchAvailableCurrencies();
    }
}
