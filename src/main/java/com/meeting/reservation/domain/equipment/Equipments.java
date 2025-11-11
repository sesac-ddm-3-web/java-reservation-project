package com.meeting.reservation.domain.equipment;

import com.meeting.reservation.domain.equipment.vo.EquipmentId;
import java.util.List;
import java.util.Map;

public class Equipments {

    private final Map<EquipmentId, Equipment> values;

    public static Equipments create(Map<EquipmentId, Equipment> equipments) {
        return new Equipments(equipments);
    }

    private Equipments(Map<EquipmentId, Equipment> values) {
        this.values = values;
    }

    public Equipment find(EquipmentId equipmentId) {
        Equipment equipment = values.get(equipmentId);

        if (equipment == null) {
            throw new EquipmentNotFoundException();
        }

        return equipment;
    }

    public List<Equipment> getEquipments() {
        return List.copyOf(values.values());
    }

    public static class EquipmentNotFoundException extends IllegalArgumentException {

        public EquipmentNotFoundException() {
            super("지정한 비품을 찾을 수 없습니다.");
        }
    }
}
