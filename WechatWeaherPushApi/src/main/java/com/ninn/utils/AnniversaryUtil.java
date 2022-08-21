package com.ninn.utils;

import org.springframework.beans.factory.annotation.Value;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AnniversaryUtil {

    public static int getAnniversary() {
        return calculateDays("2020-11-15");
    }

    public static int getBirthday1() {
        try {
            return calculateBirthday("2000-11-15");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getBirthday2() {
        try {
            return calculateBirthday("2001-01-18");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static int calculateBirthday(String date) throws ParseException {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cToday = Calendar.getInstance();
        Calendar cBirth = Calendar.getInstance();
        cBirth.setTime(myFormatter.parse(date));
        cBirth.set(Calendar.YEAR, cToday.get(Calendar.YEAR));
        int days;
        if (cBirth.get(Calendar.DAY_OF_YEAR) < cToday.get(Calendar.DAY_OF_YEAR)) {
            days = cToday.getActualMaximum(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
            days += cBirth.get(Calendar.DAY_OF_YEAR);
        } else {
            days = cBirth.get(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
        }
        return days;
    }

    public static int calculateDays(String date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int day = 0;
        try {
            long time = System.currentTimeMillis() - simpleDateFormat.parse(date).getTime();
            day = (int) (time / 86400000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }
}
