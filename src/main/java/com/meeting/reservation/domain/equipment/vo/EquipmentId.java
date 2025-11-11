package com.meeting.reservation.domain.equipment.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class EquipmentId {

    public static final EquipmentId EMPTY_EQUIPMENT_ID = new EquipmentId(null);

    public static EquipmentId create(Long value) {
        validateValue(value);

        return new EquipmentId(value);
    }

    private static void validateValue(Long value) {
        if (value == null || value <= 0L) {
            throw new IllegalArgumentException("비품 ID는 양수여야 합니다.");
        }
    }

    private final Long id;

    private EquipmentId(Long id) {
        this.id = id;
    }
}
