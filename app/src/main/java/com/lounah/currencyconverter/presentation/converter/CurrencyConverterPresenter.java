package com.lounah.currencyconverter.presentation.converter;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.lounah.currencyconverter.core.executor.ExecutorSupplier;
import com.lounah.currencyconverter.domain.interactor.CurrencyConverterInteractor;
import com.lounah.currencyconverter.presentation.viewobj.CurrencyViewObject;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

public class CurrencyConverterPresenter implements ConvertCurrencyContract.Presenter {

    private CurrencyConverterInteractor interactor;
    private ConvertCurrencyContract.View view;

    private Future<String> requestedExchangeRate;
    private FetchAvailableCurrenciesTask fetchAvailableCurrenciesTask;

    CurrencyConverterPresenter(final @NonNull CurrencyConverterInteractor interactor,
                               final @NonNull ConvertCurrencyContract.View view) {
        this.interactor = Objects.requireNonNull(interactor);
        this.view = Objects.requireNonNull(view);

        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        if (fetchAvailableCurrenciesTask == null
                || fetchAvailableCurrenciesTask.getStatus() != AsyncTask.Status.RUNNING) {
            fetchAvailableCurrenciesTask = new FetchAvailableCurrenciesTask(view, this);
            fetchAvailableCurrenciesTask.execute();
        }
    }

    @Override
    public void destroy() {
        this.view = null;
        if (fetchAvailableCurrenciesTask != null
                && fetchAvailableCurrenciesTask.getStatus() == AsyncTask.Status.RUNNING) {
            fetchAvailableCurrenciesTask.cancel(true);
        }
        if (requestedExchangeRate != null)
            requestedExchangeRate = null;
    }

    @Override
    public void fetchExchangeRate(int currencyCodeFrom, int currencyCodeTo, double valueToExchange) {
        requestedExchangeRate = ExecutorSupplier.getInstance().forBackgroundTasks()
                .submit(() -> interactor.getExchangeRate(currencyCodeFrom, currencyCodeTo, valueToExchange));
        try {
            String result = requestedExchangeRate.get();
            view.showConvertedCurrency(result);
        } catch (Exception e) {
            view.showError();
        } finally {
            view.hideLoading();
        }
    }

    @Override
    public List<CurrencyViewObject> fetchAvailableCurrencies() {
        return interactor.getAvailableCurrencies();
    }

    private static final class FetchAvailableCurrenciesTask extends AsyncTask<Void, Void, List<CurrencyViewObject>> {
        private WeakReference<ConvertCurrencyContract.View> viewRef;
        private WeakReference<ConvertCurrencyContract.Presenter> presenterRef;

        FetchAvailableCurrenciesTask(final ConvertCurrencyContract.View view, final ConvertCurrencyContract.Presenter presenter) {
            viewRef = new WeakReference<>(view);
            presenterRef = new WeakReference<>(presenter);
        }

        @Override
        protected void onPreExecute() {
            viewRef.get().showLoading();
        }

        @Override
        protected void onPostExecute(List<CurrencyViewObject> currencyViewObjects) {
            if (currencyViewObjects.isEmpty()) {
                viewRef.get().showError();
            } else {
                viewRef.get().showAvailableCurrenciesList(currencyViewObjects);
                viewRef.get().hideLoading();
            }
        }

        @Override
        protected List<CurrencyViewObject> doInBackground(Void... voids) {
            return presenterRef.get().fetchAvailableCurrencies();
        }
    }
}


