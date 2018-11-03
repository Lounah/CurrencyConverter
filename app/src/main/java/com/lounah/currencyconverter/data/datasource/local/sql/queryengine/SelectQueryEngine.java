package com.lounah.currencyconverter.data.datasource.local.sql.queryengine;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

public abstract class SelectQueryEngine<T> implements SQLQueryEngine<T> {
    @Override
    public T execute(String query, SQLiteDatabase db, String tableName, ContentValues contentValues, int onConflictStrategy) {
        return null;
    }

    @Override
    public List<T> executeRawQuery(String query, SQLiteDatabase db, String tableName, ContentValues contentValues, int onConflictStrategy) {

        List<T> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                T selectedItem = buildEntityFromCursor(cursor);
                result.add(selectedItem);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    public abstract T buildEntityFromCursor(Cursor cursor);
}
