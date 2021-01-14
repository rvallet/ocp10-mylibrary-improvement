package com.library.mslibrary.utils;

import java.util.Calendar;
import java.util.Date;

public class DateTools {

    public static Date addDays(Date date, int nbDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, nbDay);
        return cal.getTime();
    }
}