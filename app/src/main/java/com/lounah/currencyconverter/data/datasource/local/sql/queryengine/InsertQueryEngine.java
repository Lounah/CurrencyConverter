package com.lounah.currencyconverter.data.datasource.local.sql.queryengine;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.lounah.currencyconverter.core.executor.ExecutorSupplier;

import java.util.List;

public class InsertQueryEngine implements SQLQueryEngine<Long> {
    @Override
    public Long execute(String query, SQLiteDatabase db, String tableName, ContentValues contentValues, int onConflictStrategy) {
        ExecutorSupplier.getInstance().forBackgroundTasks().execute(() ->
                db.insertWithOnConflict(tableName,
                null,
                contentValues,
                onConflictStrategy
        ));
        return 0L;
    }

    @Override
    public List<Long> executeRawQuery(String query, SQLiteDatabase db, String tableName, ContentValues contentValues, int onConflictStrategy) {
        return null;
    }
}
