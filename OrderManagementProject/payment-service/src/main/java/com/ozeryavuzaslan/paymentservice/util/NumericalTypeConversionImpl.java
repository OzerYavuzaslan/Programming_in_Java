package com.ozeryavuzaslan.paymentservice.util;

import com.ozeryavuzaslan.basedomains.util.LocalSeparator;
import com.ozeryavuzaslan.basedomains.util.NumericalTypeConversion;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NumericalTypeConversionImpl implements NumericalTypeConversion {
    @Override
    public long convertDoubleToLongWithoutLosingPrecision(double doubleValue, int places) {
        doubleValue = customRound(doubleValue, places);
        String localDecimalSeparator = "[" + LocalSeparator.getLocalSeparator() + "]";
        String[] strValue = String.valueOf(doubleValue).split(localDecimalSeparator);

        if (strValue.length == 2 && Integer.parseInt(strValue[1]) == 0)
            return (long) doubleValue;

        String concataneOfBeforeAndAfterValuesOfThePoint = strValue[0] + strValue[1]; // 5.5 --> 55

        if (strValue[1].length() == 1)
            concataneOfBeforeAndAfterValuesOfThePoint += "0";

        return Integer.parseInt(concataneOfBeforeAndAfterValuesOfThePoint);
    }

    @Override
    public double convertLongToProperDouble(long intValue) {
        return ((double) intValue) / 100;
    }

    private double customRound(double doubleValue, int places) {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(doubleValue));
        return bigDecimal.setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

/*
        public static int convertDoubleToIntWithoutLosingPrecision(double doubleValue) {
        while ((doubleValue * 10) % 10 != 0)
            doubleValue *= 10;

        return (int) doubleValue;
    }
*/
}