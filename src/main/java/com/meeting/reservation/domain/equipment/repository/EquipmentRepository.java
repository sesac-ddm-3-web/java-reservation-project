package com.meeting.reservation.domain.equipment.repository;

import com.meeting.reservation.domain.equipment.Equipments;
import com.meeting.reservation.domain.room.vo.MeetingRoomId;

public interface EquipmentRepository {

    Equipments findAll(MeetingRoomId meetingRoomId);
}
