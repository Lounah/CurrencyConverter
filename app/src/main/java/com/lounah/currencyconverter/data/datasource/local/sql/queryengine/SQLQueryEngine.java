package com.lounah.currencyconverter.data.datasource.local.sql.queryengine;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public interface SQLQueryEngine<T> {
    T execute(
            final String query,
              SQLiteDatabase db,
              String tableName,
              ContentValues contentValues,
              int onConflictStrategy
    );

    List<T> executeRawQuery(
            final String query,
            SQLiteDatabase db,
            String tableName,
            ContentValues contentValues,
            int onConflictStrategy
    );
}
