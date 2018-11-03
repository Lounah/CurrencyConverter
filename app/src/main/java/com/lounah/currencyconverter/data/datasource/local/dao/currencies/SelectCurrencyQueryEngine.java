package com.lounah.currencyconverter.data.datasource.local.dao.currencies;

import android.database.Cursor;

import com.lounah.currencyconverter.data.datasource.local.sql.queryengine.SelectQueryEngine;
import com.lounah.currencyconverter.data.entity.CurrencyBean;

public class SelectCurrencyQueryEngine extends SelectQueryEngine<CurrencyBean> {

    @Override
    public CurrencyBean buildEntityFromCursor(final Cursor cursor) {
        return new CurrencyBean(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getString(3),
                cursor.getDouble(4)
        );

    }
}
