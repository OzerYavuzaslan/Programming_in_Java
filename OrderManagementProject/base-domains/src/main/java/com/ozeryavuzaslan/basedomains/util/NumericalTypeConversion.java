package com.ozeryavuzaslan.basedomains.util;

public interface NumericalTypeConversion {
    long convertDoubleToLongWithoutLosingPrecision(double doubleValue);
    double convertLongToProperDouble(long intValue);
}