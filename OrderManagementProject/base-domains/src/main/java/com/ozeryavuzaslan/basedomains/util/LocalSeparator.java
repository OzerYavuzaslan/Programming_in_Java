package com.ozeryavuzaslan.basedomains.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LocalSeparator {
    public static String getLocalSeparator(){
        return String.valueOf(new DecimalFormatSymbols(Locale.getDefault()).getDecimalSeparator());
    }
}