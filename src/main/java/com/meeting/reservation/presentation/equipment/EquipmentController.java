package com.meeting.reservation.presentation.equipment;

import com.meeting.reservation.application.EquipmentService;
import com.meeting.reservation.domain.equipment.Equipments;
import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import com.meeting.reservation.presentation.equipment.dto.response.EquipmentCollectionResponse;
import com.meeting.reservation.presentation.equipment.dto.response.EquipmentsResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @GetMapping
    public ResponseEntity<EquipmentCollectionResponse> findAll() {
        Map<MeetingRoomId, Equipments> equipments = equipmentService.findAll();
        EquipmentCollectionResponse response = EquipmentCollectionResponse.from(equipments);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{meetingRoomId}")
    public ResponseEntity<EquipmentsResponse> findAll(@PathVariable Long meetingRoomId) {
        Equipments equipments = equipmentService.findAll(meetingRoomId);
        EquipmentsResponse response = EquipmentsResponse.from(equipments);

        return ResponseEntity.ok(response);
    }
}
