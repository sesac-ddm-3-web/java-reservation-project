package com.meeting.reservation.presentation.equipment.dto.response;

import com.meeting.reservation.domain.equipment.Equipment;
import com.meeting.reservation.domain.equipment.Equipments;
import java.util.List;

public record EquipmentsResponse(List<EquipmentResponse> equipments) {

    public record EquipmentResponse(Long id, String name, int quantity) {

        public static EquipmentResponse from(Equipment equipment) {
            return new EquipmentResponse(equipment.getId().getId(), equipment.getName(), equipment.getQuantity());
        }
    }

    public static EquipmentsResponse from(Equipments equipments) {
        List<EquipmentResponse> responses = equipments.getEquipments()
                                                      .stream()
                                                      .map(EquipmentResponse::from)
                                                      .toList();

        return new EquipmentsResponse(responses);
    }
}
