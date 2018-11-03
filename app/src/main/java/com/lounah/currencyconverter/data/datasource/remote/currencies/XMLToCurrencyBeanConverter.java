package com.lounah.currencyconverter.data.datasource.remote.currencies;

import com.lounah.currencyconverter.data.datasource.remote.converter.XMLToBeanConverter;
import com.lounah.currencyconverter.data.entity.CurrencyBean;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLToCurrencyBeanConverter implements XMLToBeanConverter<String, List<CurrencyBean>> {

    @Override
    public List<CurrencyBean> convert(String xml) throws IOException, SAXException, ParserConfigurationException {
        List<CurrencyBean> result = new ArrayList<>();

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(new InputSource(new StringReader(xml)));
        Node root = document.getDocumentElement();

        NodeList currencies = root.getChildNodes();
        for (int i = 0; i < currencies.getLength(); i++) {
            Node currency = currencies.item(i);
            CurrencyBean currencyBean = new CurrencyBean();
            if (currency.getNodeType() != Node.TEXT_NODE) {
                NodeList fields = currency.getChildNodes();
                for (int j = 0; j < fields.getLength(); j++) {
                    Node field = fields.item(j);
                    if (field.getNodeType() != Node.TEXT_NODE) {
                        if (field.getNodeName().equals("Nominal")) {
                            currencyBean.setNominal(Integer.valueOf(field.getTextContent()));
                        }
                        if (field.getNodeName().equals("CharCode")) {
                            currencyBean.setCharCode(String.valueOf(field.getTextContent()));
                        }
                        if (field.getNodeName().equals("Name")) {
                            currencyBean.setName(field.getTextContent());
                        }
                        if (field.getNodeName().equals("Value")) {
                            currencyBean.setValue(Double.valueOf(field.getTextContent().replace(",", ".")));
                        }
                        if (field.getNodeName().equals("NumCode")) {
                            currencyBean.setNumCode(Integer.valueOf(field.getTextContent()));
                        }
                    }
                }
                result.add(currencyBean);
            }
        }
        return result;
    }
}
