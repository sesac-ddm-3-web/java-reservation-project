package com.meeting.reservation.domain.reservation.vo;

import com.meeting.reservation.domain.equipment.vo.EquipmentId;
import lombok.Getter;

@Getter
public class EquipmentUsage {

    private final EquipmentId equipmentId;
    private final String name;
    private final int quantity;

    public static EquipmentUsage create(EquipmentId equipmentId, String name, int quantity) {
        validateEquipmentId(equipmentId);
        validateName(name);
        validateQuantity(quantity);

        return new EquipmentUsage(equipmentId, name, quantity);
    }

    private static void validateEquipmentId(EquipmentId equipmentId) {
        if (equipmentId == null) {
            throw new IllegalArgumentException("비품 ID는 비어 있을 수 없습니다.");
        }
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("비품 이름은 비어 있을 수 없습니다.");
        }
    }

   private static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("비품 수량은 양수여야 합니다.");
        }
   }

    private EquipmentUsage(EquipmentId equipmentId, String name, int quantity) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.quantity = quantity;
    }
}
