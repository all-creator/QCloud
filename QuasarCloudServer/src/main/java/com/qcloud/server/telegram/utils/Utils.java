package com.qcloud.server.telegram.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static String getDateTimeNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(getPattern()));
    }

    public static String dateTimeToPattern(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(getPattern()));
    }

    public static LocalDateTime dateTimeFromPattern(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(getPattern()));
    }

    public static String getPattern() {
        return DATE_TIME_FORMAT;
    }


}
