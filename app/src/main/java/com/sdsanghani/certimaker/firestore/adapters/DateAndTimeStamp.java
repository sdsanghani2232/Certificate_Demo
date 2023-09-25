package com.sdsanghani.certimaker.firestore.adapters;

import android.annotation.SuppressLint;

import java.time.LocalDateTime;

public class DateAndTimeStamp {
    LocalDateTime stamp = LocalDateTime.now();
    int year = stamp.getYear();
    int month = stamp.getMonthValue();
    int day = stamp.getDayOfMonth();
    int hour =stamp.getHour();
    int minute = stamp.getMinute();
    int second = stamp.getSecond();


    @SuppressLint("DefaultLocale")
    public String DateTime()
    {
        return String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
    }

    @SuppressLint("DefaultLocale")
    public String Date()
    {
        return String.format("%04d-%02d-%02d", year, month, day);
    }
}
