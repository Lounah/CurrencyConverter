package com.lounah.currencyconverter.data.db;

import android.database.sqlite.SQLiteDatabase;

import com.lounah.currencyconverter.data.datasource.local.dao.currencies.CurrenciesDao;
import com.lounah.currencyconverter.data.datasource.local.sql.queryengine.SQLQueryEngine;
import com.lounah.currencyconverter.data.entity.CurrencyBean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;

@RunWith(JUnit4.class)
public class CurrenciesDaoTest {

    @Mock
    private SQLiteDatabase db;
    @Mock
    private SQLQueryEngine<CurrencyBean> selectQueryEngine;
    @Mock
    private SQLQueryEngine<Long> insertQueryEngine;

    private CurrenciesDao dao;

    private CurrencyBean stubBean = new CurrencyBean(0, "", 1, "", 1);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        dao = new CurrenciesDao(db, selectQueryEngine, insertQueryEngine);
    }

    @Test
    public void addsCurrency_correctly() {
        dao.add(stubBean);

        Mockito.verify(insertQueryEngine).execute("", db, Mockito.anyString(), Mockito.any(), Mockito.anyInt());
    }

    @Test
    public void getsAll_correctly() {
        List<CurrencyBean> stubBeans = new ArrayList<>();
        stubBeans.add(stubBean);
        Mockito.when(selectQueryEngine.executeRawQuery(eq(Mockito.anyString()), db, eq(Mockito.anyString()), null, eq(Mockito.anyInt()))).thenReturn(stubBeans);

        dao.getAll();

        Mockito.verify(selectQueryEngine).executeRawQuery(eq(Mockito.anyString()), db, eq(Mockito.anyString()), null, eq(Mockito.anyInt()));
    }
}
