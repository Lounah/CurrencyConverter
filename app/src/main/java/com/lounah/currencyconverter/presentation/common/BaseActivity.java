package com.lounah.currencyconverter.presentation.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getLayoutResource();
    protected abstract void initUIComponents();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutRes = getLayoutResource();
        setContentView(layoutRes);
        initUIComponents();
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int msgRes) {
        Toast.makeText(this, msgRes, Toast.LENGTH_SHORT).show();
    }
}
