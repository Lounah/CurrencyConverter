package com.lounah.currencyconverter.data.datasource.local.dao.currencies;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.lounah.currencyconverter.data.datasource.local.dao.BaseDao;
import com.lounah.currencyconverter.data.datasource.local.sql.DatabaseContract;
import com.lounah.currencyconverter.data.datasource.local.sql.queryengine.SQLQueryEngine;
import com.lounah.currencyconverter.data.entity.CurrencyBean;

import java.util.List;

public class CurrenciesDao implements BaseDao<CurrencyBean> {

    private SQLiteDatabase sqLiteDatabase;
    private SQLQueryEngine<CurrencyBean> selectQueryEngine;
    private SQLQueryEngine<Long> insertQueryEngine;

    public CurrenciesDao(final SQLiteDatabase sqLiteDatabase,
                         final SQLQueryEngine<CurrencyBean> selectQueryEngine,
                         final SQLQueryEngine<Long> insertQueryEngine) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.selectQueryEngine = selectQueryEngine;
        this.insertQueryEngine = insertQueryEngine;
    }

    @Override
    public long add(final CurrencyBean item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.CurrenciesTable.COLUMN_NAME_CHAR_CODE, item.getCharCode());
        contentValues.put(DatabaseContract.CurrenciesTable.COLUMN_NAME_NAME, item.getName());
        contentValues.put(DatabaseContract.CurrenciesTable.COLUMN_NAME_NOMINAL, item.getNominal());
        contentValues.put(DatabaseContract.CurrenciesTable.COLUMN_NAME_NUM_CODE, item.getNumCode());
        contentValues.put(DatabaseContract.CurrenciesTable.COLUMN_NAME_VALUE, item.getValue());

        return insertQueryEngine.execute(
                "",
                sqLiteDatabase,
                DatabaseContract.CurrenciesTable.TABLE_NAME,
                contentValues,
                SQLiteDatabase.CONFLICT_REPLACE);
    }

    /*
        Ужасное решение, я знаю
     */
    @Override
    public void addAll(final List<CurrencyBean> items) {
        for (CurrencyBean bean : items) add(bean);
    }

    public CurrencyBean getByCode(final int code) {
        String selectQuery = "SELECT * FROM "
                + DatabaseContract.CurrenciesTable.TABLE_NAME
                + " WHERE "
                + DatabaseContract.CurrenciesTable.COLUMN_NAME_NUM_CODE + " = " + code;

        return selectQueryEngine.executeRawQuery(
                selectQuery,
                sqLiteDatabase,
                DatabaseContract.CurrenciesTable.TABLE_NAME,
                null,
                0
        ).get(0);
    }

    @Override
    public List<CurrencyBean> getAll() {
        String selectQuery = "SELECT * FROM " + DatabaseContract.CurrenciesTable.TABLE_NAME;

        return selectQueryEngine.executeRawQuery(
                selectQuery,
                sqLiteDatabase,
                DatabaseContract.CurrenciesTable.TABLE_NAME,
                null,
                0
        );
    }
}