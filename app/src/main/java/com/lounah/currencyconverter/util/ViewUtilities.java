package com.lounah.currencyconverter.util;

import android.content.Context;

public class ViewUtilities {

    private ViewUtilities() {
    }

    public static int dpToPx(int dp, Context context) {
        return Math.round(dp * (context.getResources().getDisplayMetrics().densityDpi / 160f));
    }
}
