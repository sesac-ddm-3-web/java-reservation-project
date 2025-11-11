package org.example.javareservationproject.domain.reservation;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Reservation implements Comparable<Reservation> {
    private Long id;
    private Long meetingRoomId;
    private ReservationTime time;
    private ReservationInfo info;
    private LocalDateTime reservedAt;

    private Reservation(Long meetingRoomId, ReservationTime time, ReservationInfo info, LocalDateTime reservedAt) {
        this.meetingRoomId = meetingRoomId;
        this.info = info;
        this.time = time;
        this.reservedAt = reservedAt;
    }

    public static Reservation create(Long meetingRoomId, ReservationTime time, ReservationInfo info, LocalDateTime reservedAt) {
        return new Reservation(meetingRoomId, time, info, reservedAt);
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;

        Reservation that = (Reservation)o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public int compareTo(Reservation o) {
        // 날짜, 시작 시간 오름차순
        int dateCompare = this.time.getDate().compareTo(o.time.getDate());
        if (dateCompare != 0) {
            return dateCompare;
        }

        return this.time.getStartTime().compareTo(o.time.getStartTime());
    }
}

