package com.ozeryavuzaslan.basedomains.util;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class LocalSeparator {
    private LocalSeparator(){
    }

    public static String getLocalSeparator(){
        return String.valueOf(new DecimalFormatSymbols(Locale.getDefault()).getDecimalSeparator());
    }
}