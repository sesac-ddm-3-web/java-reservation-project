package com.meeting.reservation.domain.reservation;

import com.meeting.reservation.domain.equipment.vo.EquipmentId;
import com.meeting.reservation.domain.reservation.vo.EquipmentUsages;
import com.meeting.reservation.domain.reservation.vo.Organizer;
import com.meeting.reservation.domain.reservation.vo.ReservationId;
import com.meeting.reservation.domain.reservation.vo.TimeSlot;
import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public class Reservation {

    private final ReservationId id;
    private final MeetingRoomId meetingRoomId;
    private final TimeSlot timeSlot;
    private final int attendeeCount;
    private final Organizer organizer;
    private final EquipmentUsages equipmentUsages;

    Reservation(
            ReservationId id,
            MeetingRoomId meetingRoomId,
            TimeSlot timeSlot,
            int attendeeCount,
            Organizer organizer,
            EquipmentUsages equipmentUsages
    ) {
        this.id = id;
        this.meetingRoomId = meetingRoomId;
        this.timeSlot = timeSlot;
        this.attendeeCount = attendeeCount;
        this.organizer = organizer;
        this.equipmentUsages = equipmentUsages;
    }

    public Reservation withAssignedId(Long id) {
        ReservationId reservationId = ReservationId.create(id);

        return new Reservation(
                reservationId,
                this.meetingRoomId,
                this.timeSlot,
                this.attendeeCount,
                this.organizer,
                this.equipmentUsages
        );
    }

    public Reservation shift(ReservationFrequency frequency) {
        TimeSlot shiftTimeSlot = this.timeSlot.shiftBy(frequency);

        return new Reservation(
                ReservationId.EMPTY_RESERVATION_ID,
                this.meetingRoomId,
                shiftTimeSlot,
                this.attendeeCount,
                this.organizer,
                this.equipmentUsages
        );
    }

    public boolean overlapTime(Reservation other) {
        return this.timeSlot.overlapTime(other.timeSlot);
    }

    public boolean overlapTime(TimeSlot timeSlot) {
        return this.timeSlot.overlapTime(timeSlot);
    }

    public boolean afterStartTime(LocalDateTime now) {
        return timeSlot.afterStartTime(now);
    }

    public boolean matchPassword(String password) {
        return organizer.matchPassword(password);
    }

    public boolean isEqualId(Long id) {
        return this.id.isEqualId(id);
    }

    public int getEquipmentQuantity(EquipmentId equipmentId) {
        return equipmentUsages.getQuantity(equipmentId);
    }
}
