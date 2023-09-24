package com.ozeryavuzaslan.paymentservice.util;

import com.ozeryavuzaslan.basedomains.util.LocalSeparator;
import com.ozeryavuzaslan.basedomains.util.NumericalTypeConversion;
import org.springframework.stereotype.Service;

@Service
public final class NumericalTypeConversionImpl implements NumericalTypeConversion {
    private NumericalTypeConversionImpl(){
    }

    @Override
    public int convertDoubleToIntWithoutLosingPrecision(double doubleValue) {
        String localDecimalSeparator = "[" + LocalSeparator.getLocalSeparator() + "]";
        String[] strValue = String.valueOf(doubleValue).split(localDecimalSeparator);

        if (strValue.length == 2 && Integer.parseInt(strValue[1]) == 0)
            return (int) doubleValue;

        String concataneOfBeforeAndAfterValuesOfThePoint = strValue[0] + strValue[1]; // 5.5 --> 55

        if(strValue[1].length() == 1)
            concataneOfBeforeAndAfterValuesOfThePoint += "0";

        return Integer.parseInt(concataneOfBeforeAndAfterValuesOfThePoint);
    }

    @Override
    public double convertIntToProperDouble(int intValue){
        return ((double) intValue) / 100;
    }

    /*
        public static int convertDoubleToIntWithoutLosingPrecision(double doubleValue) {
        while ((doubleValue * 10) % 10 != 0)
            doubleValue *= 10;

        return (int) doubleValue;
    }
     */
}