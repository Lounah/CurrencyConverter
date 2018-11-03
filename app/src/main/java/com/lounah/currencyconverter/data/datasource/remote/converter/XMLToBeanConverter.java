package com.lounah.currencyconverter.data.datasource.remote.converter;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public interface XMLToBeanConverter<S, R> {
    R convert (final S xml) throws IOException, SAXException, ParserConfigurationException;
}
