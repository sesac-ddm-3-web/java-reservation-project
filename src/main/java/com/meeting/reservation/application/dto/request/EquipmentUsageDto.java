package com.meeting.reservation.application.dto.request;

import com.meeting.reservation.domain.equipment.vo.EquipmentId;

public record EquipmentUsageDto(EquipmentId id, int quantity) {
}
