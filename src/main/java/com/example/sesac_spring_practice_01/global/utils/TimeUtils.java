package com.example.sesac_spring_practice_01.global.utils;


import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtils {
    public static LocalDateTime snapToMinute(LocalDateTime t) {
        return t.withSecond(0).withNano(0);
    }

    public static LocalTime snapToMinute(LocalTime t) {
        return t.withSecond(0).withNano(0);
    }
}