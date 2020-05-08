package com.ljqiii.hairlesscommon.utils;

public class TimeUtils {

    public static String convertMilliSecond2TimeHmsStr(long millisecond) {
        long sumsecond = millisecond / 1000;
        int hour = (int) sumsecond / 3600;
        int min = (int) (sumsecond % 3600) / 60;
        int second = (int) sumsecond % 60;

        return hour + ":" + min + ":" + second;
    }
}
