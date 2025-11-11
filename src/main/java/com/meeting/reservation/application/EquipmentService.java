package com.meeting.reservation.application;

import com.meeting.reservation.domain.equipment.Equipments;
import com.meeting.reservation.domain.equipment.repository.EquipmentRepository;
import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public Map<MeetingRoomId, Equipments> findAll() {
        return equipmentRepository.findAll();
    }

    public Equipments findAll(Long meetingRoomId) {
        return equipmentRepository.findAll(MeetingRoomId.create(meetingRoomId));
    }
}
