package com.example.sesac_spring_practice_01.global;

import java.util.concurrent.atomic.AtomicLong;

public class ReservationIds {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);
    public static long nextId() {
        return ID_GENERATOR.getAndIncrement();
    }
}
