package com.meeting.reservation.domain.reservation.vo;

import com.meeting.reservation.domain.equipment.vo.EquipmentId;
import java.util.Collections;
import java.util.Map;

public class EquipmentUsages {

    private final Map<EquipmentId, EquipmentUsage> values;

    public static EquipmentUsages create(Map<EquipmentId, EquipmentUsage> values) {
        return new EquipmentUsages(values);
    }

    private EquipmentUsages(Map<EquipmentId, EquipmentUsage> values) {
        this.values = values;
    }

    public int getQuantity(EquipmentId equipmentId) {
        EquipmentUsage equipmentUsage = values.get(equipmentId);

        if (equipmentUsage == null) {
            return 0;
        }

        return equipmentUsage.getQuantity();
    }

    public Map<EquipmentId, EquipmentUsage> getEquipmentUsages() {
        return Collections.unmodifiableMap(values);
    }
}
