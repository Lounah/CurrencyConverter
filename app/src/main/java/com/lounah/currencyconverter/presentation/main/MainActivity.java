package com.lounah.currencyconverter.presentation.main;

import com.lounah.currencyconverter.R;
import com.lounah.currencyconverter.presentation.common.BaseActivity;
import com.lounah.currencyconverter.presentation.converter.CurrencyConverterFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUIComponents() {
        setUpBaseFragment();
    }

    private void setUpBaseFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new CurrencyConverterFragment())
                .commit();
    }
}
