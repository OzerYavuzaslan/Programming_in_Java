package com.ozeryavuzaslan.basedomains.util;

public interface NumericalTypeConversion {
    int convertDoubleToIntWithoutLosingPrecision(double doubleValue);
    double convertIntToProperDouble(int intValue);
}