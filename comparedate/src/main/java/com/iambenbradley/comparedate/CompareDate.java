package com.iambenbradley.comparedate;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class CompareDate {

    private boolean compare(SimpleDateFormat simpleDateFormat, Long date) {
        Date currentDate = new Date();
        Date compareDate = new Date(date);
        return simpleDateFormat.format(currentDate).equals(simpleDateFormat.format(compareDate));
    }

    public boolean toThisMinute(Long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        return compare(simpleDateFormat,date);
    }

    public boolean toThisHour(Long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH");
        return compare(simpleDateFormat,date);
    }

    public boolean toThisDay(Long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return compare(simpleDateFormat,date);
    }

    public boolean toThisWeek(Long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        return compare(simpleDateFormat,date);
    }

    public boolean toThisMonth(Long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        return compare(simpleDateFormat,date);
    }

    public boolean toThisYear(Long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return compare(simpleDateFormat,date);
    }

    public boolean toLastMinute(Long date) {
        return toLastXMinute(date, 1);
    }

    public boolean toLastXMinute(Long date, int xMinutes) {
        Instant time = Instant.ofEpochMilli(date);
        Instant maxTime = Instant.now().minus(Duration.ofMinutes(xMinutes));
        return time.isBefore(maxTime);
    }

    public boolean toLastHour(Long date) {
        Instant time = Instant.ofEpochMilli(date);
        Instant maxTime = Instant.now().minus(Duration.ofMinutes(60));
        return time.isBefore(maxTime);
    }

    public boolean toLastDay(Long date) {
        Instant time = Instant.ofEpochMilli(date);
        Instant maxTime = Instant.now().minus(Duration.ofHours(24));
        return time.isBefore(maxTime);
    }

    public boolean toLastMonth(Long date) {
        Instant time = Instant.ofEpochMilli(date);
        Instant maxTime = Instant.now().minus(Duration.ofDays(30));
        return time.isBefore(maxTime);
    }

    public boolean toLastYear(Long date) {
        Instant time = Instant.ofEpochMilli(date);
        Instant maxTime = Instant.now().minus(Duration.ofDays(365));
        return time.isBefore(maxTime);
    }
}
