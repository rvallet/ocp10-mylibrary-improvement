package com.library.msbatch.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateTools {

    public static Date yesterday(){
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static Date yesterdayTheDayBefore(){
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        return cal.getTime();
    }

    public static String dateToStringPatternForEmail (Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }
}
