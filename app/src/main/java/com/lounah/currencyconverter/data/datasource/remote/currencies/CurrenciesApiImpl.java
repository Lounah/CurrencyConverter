package com.lounah.currencyconverter.data.datasource.remote.currencies;

import android.support.annotation.VisibleForTesting;

import com.lounah.currencyconverter.data.datasource.remote.converter.XMLToBeanConverter;
import com.lounah.currencyconverter.data.entity.CurrencyBean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CurrenciesApiImpl implements CurrenciesApi {

    /*
        used only as a per-session cache
     */
    private List<CurrencyBean> cachedCurrencies = new ArrayList<>();
    private XMLToBeanConverter<String, List<CurrencyBean>> xmlConverter;

    public CurrenciesApiImpl(final XMLToBeanConverter<String, List<CurrencyBean>> xmlConverter) {
        this.xmlConverter = xmlConverter;
    }

    @Override
    public List<CurrencyBean> fetchCurrencies(final RequestCallback callback) {
        if (cacheIsEmpty()) {
            return fetchCurrenciesFromApi(callback);
        } else return cachedCurrencies;
    }

    @Override
    public CurrencyBean fetchCurrencyByCode(final int code, final RequestCallback callback) {
        List<CurrencyBean> currencyBeans = fetchCurrencies(callback);
        for (CurrencyBean currency : currencyBeans) {
            if (currency.getNumCode() == code)
                return currency;
        }
        return new CurrencyBean();
    }

    @Override
    public boolean cacheIsEmpty() {
        return cachedCurrencies.isEmpty();
    }

    private List<CurrencyBean> fetchCurrenciesFromApi(final RequestCallback callback) {
        URL url;
        HttpURLConnection con = null;
        try {
            url = new URL(ENDPOINT_URL);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            con.getInputStream(), "CP1251"));

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();

            List<CurrencyBean> beans = xmlConverter.convert(response.toString());
            callback.onComplete(beans);
            return beans;

        } catch (Exception e) {
            e.printStackTrace();
            callback.onError();
        } finally {
            if (con != null)
                con.disconnect();
        }
        return null;
    }

    @VisibleForTesting
    public void setCachedCurrencies(List<CurrencyBean> currencies) {
        cachedCurrencies.clear();
        cachedCurrencies.addAll(currencies);
    }
}
