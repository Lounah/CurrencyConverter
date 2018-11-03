package com.lounah.currencyconverter.core;

import java.util.List;

public interface Mapper<T, R> {
    R map(T what);
    List<R> map(List<T> what);
}
