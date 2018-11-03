package com.lounah.currencyconverter.data.datasource.local.sql;

import android.provider.BaseColumns;

public final class DatabaseContract {

    private DatabaseContract() {}

    public static abstract class CurrenciesTable implements BaseColumns {

        public static final String TABLE_NAME = "currencies";
        public static final String COLUMN_NAME_NUM_CODE = "numCode";
        public static final String COLUMN_NAME_CHAR_CODE = "charCode";
        public static final String COLUMN_NAME_NOMINAL = "nominal";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_VALUE = "value";

        public static final String CREATE_DATABASE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                        + CurrenciesTable.COLUMN_NAME_NUM_CODE + " INTEGER PRIMARY KEY, "
                        + CurrenciesTable.COLUMN_NAME_CHAR_CODE + " TEXT,"
                        + CurrenciesTable.COLUMN_NAME_NOMINAL + " INTEGER (10), "
                        + CurrenciesTable.COLUMN_NAME_NAME + " TEXT, "
                        + CurrenciesTable.COLUMN_NAME_VALUE + " DOUBLE (10) );";

        public static final String DELETE_DATABASE =
                "DROP TABLE IF EXIST " + CurrenciesTable.TABLE_NAME;
    }

}
