package com.lounah.currencyconverter.di;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lounah.currencyconverter.data.datasource.local.dao.currencies.CurrenciesDao;
import com.lounah.currencyconverter.data.datasource.local.dao.currencies.SelectCurrencyQueryEngine;
import com.lounah.currencyconverter.data.datasource.local.sql.DatabaseHelper;
import com.lounah.currencyconverter.data.datasource.local.sql.queryengine.InsertQueryEngine;
import com.lounah.currencyconverter.data.datasource.remote.converter.XMLToBeanConverter;
import com.lounah.currencyconverter.data.datasource.remote.currencies.CurrenciesApi;
import com.lounah.currencyconverter.data.datasource.remote.currencies.CurrenciesApiImpl;
import com.lounah.currencyconverter.data.datasource.remote.currencies.XMLToCurrencyBeanConverter;
import com.lounah.currencyconverter.data.entity.CurrencyBean;
import com.lounah.currencyconverter.data.mapper.CurrencyBeanToCurrencyMapper;
import com.lounah.currencyconverter.data.repository.CurrencyRepositoryImpl;
import com.lounah.currencyconverter.domain.interactor.CurrencyConverterInteractor;
import com.lounah.currencyconverter.domain.mapper.CurrencyToViewObjectMapper;
import com.lounah.currencyconverter.core.Mapper;
import com.lounah.currencyconverter.domain.model.Currency;
import com.lounah.currencyconverter.domain.repository.CurrencyRepository;
import com.lounah.currencyconverter.presentation.viewobj.CurrencyViewObject;

import java.util.List;

public class Injection {

    private Injection() {
    }

    public static CurrencyConverterInteractor provideCurrencyConverterInteractor(Context context) {
        return new CurrencyConverterInteractor(provideCurrencyRepository(context), provideCurrencyToViewObjectMapper());
    }

    private static CurrencyRepository provideCurrencyRepository(Context context) {
        return new CurrencyRepositoryImpl(provideApi(), provideDb(context), provideCurrencyBeanToCurrencyMapper());
    }

    private static Mapper<Currency, CurrencyViewObject> provideCurrencyToViewObjectMapper() {
        return new CurrencyToViewObjectMapper();
    }

    private static Mapper<CurrencyBean, Currency> provideCurrencyBeanToCurrencyMapper() {
        return new CurrencyBeanToCurrencyMapper();
    }

    private static CurrenciesApi provideApi() {
        return new CurrenciesApiImpl(provideCurrencyXMLConverter());
    }

    private static XMLToBeanConverter<String, List<CurrencyBean>> provideCurrencyXMLConverter() {
        return new XMLToCurrencyBeanConverter();
    }

    private static CurrenciesDao provideDb(Context context) {
        return new CurrenciesDao(provideAppDb(context), provideSelectCurrencyQueryEngine(), provideInsertQueryEngine());
    }

    private static SQLiteDatabase provideAppDb(Context context) {
        return new DatabaseHelper(context).getWritableDatabase();
    }

    private static SelectCurrencyQueryEngine provideSelectCurrencyQueryEngine() {
        return new SelectCurrencyQueryEngine();
    }

    private static InsertQueryEngine provideInsertQueryEngine() {
        return new InsertQueryEngine();
    }
}
