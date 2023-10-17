package com.ozeryavuzaslan.basedomains.util;

public interface NumericalTypeConversion {
    long convertDoubleToLongWithoutLosingPrecision(double doubleValue, int places);
    double convertLongToProperDouble(long intValue);
}