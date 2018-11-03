package com.lounah.currencyconverter.data.datasource.remote.currencies;

import java.util.List;

public interface RequestCallback<T> {
    void onComplete(List<T> result);

    void onError();
}
