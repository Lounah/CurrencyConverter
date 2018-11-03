package com.lounah.currencyconverter.presentation.converter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.lounah.currencyconverter.presentation.viewobj.CurrencyViewObject;
import com.lounah.currencyconverter.util.ViewUtilities;

import java.util.List;

public class SpinnerConverterAdapter extends ArrayAdapter<CurrencyViewObject> {

    private List<CurrencyViewObject> values;

    SpinnerConverterAdapter(@NonNull Context context, int resource, @NonNull List<CurrencyViewObject> objects) {
        super(context, resource, objects);
        this.values = objects;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public CurrencyViewObject getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getCodeName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(parent.getContext());

        TableRow.LayoutParams labelLayoutParams = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);

        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getName());


        labelLayoutParams.setMargins(
                ViewUtilities.dpToPx(8, getContext()),
                ViewUtilities.dpToPx(16, getContext()),
                ViewUtilities.dpToPx(16, getContext()),
                ViewUtilities.dpToPx(8, getContext())
        );

        label.setPadding(
                ViewUtilities.dpToPx(8, getContext()),
                ViewUtilities.dpToPx(16, getContext()),
                ViewUtilities.dpToPx(16, getContext()),
                ViewUtilities.dpToPx(8, getContext())
        );

        label.setLayoutParams(labelLayoutParams);
        return label;
    }
}
