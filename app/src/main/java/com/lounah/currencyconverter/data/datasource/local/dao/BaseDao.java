package com.lounah.currencyconverter.data.datasource.local.dao;

import java.util.List;

public interface BaseDao<T> {
    long add(T item);

    void addAll(List<T> items);

    List<T> getAll();
}
