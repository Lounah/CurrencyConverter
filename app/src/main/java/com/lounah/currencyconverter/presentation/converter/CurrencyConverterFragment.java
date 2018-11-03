package com.lounah.currencyconverter.presentation.converter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.lounah.currencyconverter.R;
import com.lounah.currencyconverter.di.Injection;
import com.lounah.currencyconverter.presentation.common.BaseFragment;
import com.lounah.currencyconverter.presentation.viewobj.CurrencyViewObject;

import java.util.List;
import java.util.Objects;

public class CurrencyConverterFragment extends BaseFragment implements ConvertCurrencyContract.View {

    private Spinner currenciesConvertFrom;
    private Spinner currenciesConvertTo;
    private ImageButton btnSwapCurrencies;
    private FrameLayout loadingLayout;
    private Button btnConvert;
    private EditText etValueToConvert;
    private TextView tvConvertedResult;

    private ConvertCurrencyContract.Presenter presenter;

    private int selectedCurrencyCodeFrom = -1;
    private int selectedCurrencyCodeTo = -1;
    private double valueToConvert = 0;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_currency_converter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = new CurrencyConverterPresenter(
                Injection.provideCurrencyConverterInteractor(getActivity().getApplicationContext()),
                this
        );
    }

    @Override
    protected void initUI() {
        attachViews();

        btnConvert.setOnClickListener(v -> {
            initSelectedCurrencies();
            if (selectedCurrencyCodeFrom != -1 && selectedCurrencyCodeTo != -1 && valueToConvert != 0)
                loadExchangeRate(selectedCurrencyCodeTo, selectedCurrencyCodeFrom, valueToConvert);
        });

        btnSwapCurrencies.setOnClickListener(v -> {
            swapSpinnerItems();
            initSelectedCurrencies();
            loadExchangeRate(selectedCurrencyCodeTo, selectedCurrencyCodeFrom, valueToConvert);
        });
    }

    @Override
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        showToast(R.string.error_loading_data);
    }

    @Override
    public void showConvertedCurrency(String value) {
        tvConvertedResult.setText(value);
    }

    @Override
    public void showAvailableCurrenciesList(List<CurrencyViewObject> currencies) {
        SpinnerConverterAdapter adapter = new SpinnerConverterAdapter(
                Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item,
                currencies);
        currenciesConvertFrom.setAdapter(adapter);
        currenciesConvertTo.setAdapter(adapter);
    }

    @Override
    public void setPresenter(ConvertCurrencyContract.Presenter presenter) {
        this.presenter = Objects.requireNonNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    private void attachViews() {
        currenciesConvertFrom = (Spinner) findViewById(R.id.spinner_converter_from);
        currenciesConvertTo = (Spinner) findViewById(R.id.spinner_converter_to);
        btnSwapCurrencies = (ImageButton) findViewById(R.id.btn_converter_swap_currencies);
        btnConvert = (Button) findViewById(R.id.btn_converter_convert);
        etValueToConvert = (EditText) findViewById(R.id.et_converter_value_to_covert);
        loadingLayout = (FrameLayout) findViewById(R.id.container_converter_loading);
        tvConvertedResult = (TextView) findViewById(R.id.tv_converter_result_of_conversion);
    }

    private void loadExchangeRate(int codeFrom, int codeTo, double valueToConvert) {
        presenter.fetchExchangeRate(codeFrom, codeTo, valueToConvert);
    }

    private void swapSpinnerItems() {
        int selectedFromIndex = currenciesConvertFrom.getSelectedItemPosition();
        currenciesConvertFrom.setSelection(currenciesConvertTo.getSelectedItemPosition());
        currenciesConvertTo.setSelection(selectedFromIndex);
    }

    private void initSelectedCurrencies() {
        if (currenciesConvertFrom.getSelectedItem() != null)
            selectedCurrencyCodeFrom = ((CurrencyViewObject) currenciesConvertFrom.getSelectedItem()).getCode();
        if (currenciesConvertTo.getSelectedItem() != null)
            selectedCurrencyCodeTo = ((CurrencyViewObject) currenciesConvertTo.getSelectedItem()).getCode();

        if (!etValueToConvert.getText().toString().isEmpty())
            valueToConvert = Double.parseDouble(etValueToConvert.getText().toString());
    }
}
