package com.example.gestioninventairesystem.Utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtils {

    public static String timestamp(String date, String format) {
        Timestamp timestamp = Timestamp.valueOf(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.FRENCH);
        return dateFormat.format(timestamp);
    }
}
