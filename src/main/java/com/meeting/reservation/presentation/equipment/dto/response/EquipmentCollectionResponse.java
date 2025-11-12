package com.meeting.reservation.presentation.equipment.dto.response;

import com.meeting.reservation.domain.equipment.Equipment;
import com.meeting.reservation.domain.equipment.Equipments;
import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public record EquipmentCollectionResponse(Map<Long, List<EquipmentResponse>> equipments) {

    public record EquipmentResponse(Long id, String name, int quantity) {

        public static EquipmentResponse from(Equipment equipment) {
            return new EquipmentResponse(
                    equipment.getId()
                             .getId(),
                    equipment.getName(),
                    equipment.getQuantity()
            );
        }
    }

    public static EquipmentCollectionResponse from(Map<MeetingRoomId, Equipments> equipments) {
        Map<Long, List<EquipmentResponse>> responses = new HashMap<>();

        for (Entry<MeetingRoomId, Equipments> entry : equipments.entrySet()) {
            List<EquipmentResponse> equipmentResponses = entry.getValue()
                                                              .getEquipments()
                                                              .stream()
                                                              .map(EquipmentResponse::from)
                                                              .toList();

            responses.put(entry.getKey().getValue(), equipmentResponses);
        }

        return new EquipmentCollectionResponse(responses);
    }
}
