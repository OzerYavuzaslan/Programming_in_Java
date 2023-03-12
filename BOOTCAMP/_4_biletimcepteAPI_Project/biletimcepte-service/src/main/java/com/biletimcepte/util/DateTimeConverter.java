package com.biletimcepte.util;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

@NoArgsConstructor
public final class DateTimeConverter {
    public static LocalDateTime convert(String strDateTime) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .optionalStart()
                .appendPattern(" HH:mm:ss.SSS")
                .optionalEnd()
                .parseDefaulting(ChronoField.HOUR_OF_DAY,0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR,0)
                .parseDefaulting(ChronoField.SECOND_OF_DAY, 0)
                .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                .toFormatter();
        return LocalDateTime.parse(strDateTime, dateTimeFormatter);
    }
}
