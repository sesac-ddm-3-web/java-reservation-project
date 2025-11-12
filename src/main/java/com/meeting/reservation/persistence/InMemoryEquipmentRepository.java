package com.meeting.reservation.persistence;

import com.meeting.reservation.domain.equipment.Equipment;
import com.meeting.reservation.domain.equipment.Equipments;
import com.meeting.reservation.domain.equipment.repository.EquipmentRepository;
import com.meeting.reservation.domain.equipment.vo.EquipmentId;
import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryEquipmentRepository implements EquipmentRepository {

    private final Map<MeetingRoomId, Equipments> equipments;

    public InMemoryEquipmentRepository() {
        Map<MeetingRoomId, Equipments> equipments = new HashMap<>();

        equipments.put(MeetingRoomId.create(1L), initEquipments());
        equipments.put(MeetingRoomId.create(2L), initEquipments());
        equipments.put(MeetingRoomId.create(3L), initEquipments());
        equipments.put(MeetingRoomId.create(4L), initEquipments());
        equipments.put(MeetingRoomId.create(5L), initEquipments());
        equipments.put(MeetingRoomId.create(6L), initEquipments());
        equipments.put(MeetingRoomId.create(7L), initEquipments());

        this.equipments = equipments;
    }

    private Equipments initEquipments() {
        return Equipments.create(
                Map.of(
                        EquipmentId.create(1L), Equipment.create("빔프로젝터", 5).withAssignedId(1L),
                        EquipmentId.create(2L), Equipment.create("회의용 스피커폰", 10).withAssignedId(2L),
                        EquipmentId.create(3L), Equipment.create("화상 회의 키트", 4).withAssignedId(3L),
                        EquipmentId.create(4L), Equipment.create("화이트보드", 8).withAssignedId(4L),
                        EquipmentId.create(5L), Equipment.create("노트북", 20).withAssignedId(5L)
                )
        );
    }

    @Override
    public Equipments findAll(MeetingRoomId meetingRoomId) {
        return this.equipments.get(meetingRoomId);
    }

    @Override
    public Map<MeetingRoomId, Equipments> findAll() {
        return Collections.unmodifiableMap(equipments);
    }
}
