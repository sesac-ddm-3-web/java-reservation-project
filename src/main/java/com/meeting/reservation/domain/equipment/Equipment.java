package com.meeting.reservation.domain.equipment;

import com.meeting.reservation.domain.equipment.vo.EquipmentId;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public class Equipment {

    private final EquipmentId id;
    private final String name;
    private final int quantity;

    public static Equipment create(String name, int quantity) {
        validateName(name);
        validateQuantity(quantity);

        return new Equipment(EquipmentId.EMPTY_EQUIPMENT_ID, name, quantity);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("비품 이름은 비어 있을 수 없습니다.");
        }
    }

    private static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("비품 수량은 양수여야 합니다,");
        }
    }

    private Equipment(EquipmentId id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Equipment withAssignedId(Long id) {
        EquipmentId equipmentId = EquipmentId.create(id);

        return new Equipment(
                equipmentId,
                this.name,
                this.quantity
        );
    }

    public boolean canProvide(int requestedQuantity) {
        return this.quantity >= requestedQuantity;
    }
}
