package com.example.rummenigged.rocketchat.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    public static String extractHourAndMinute(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.US);
        return dateFormat.format(date);
    }
}
