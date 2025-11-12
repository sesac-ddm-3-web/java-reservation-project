package com.meeting.reservation.global;

import com.meeting.reservation.domain.equipment.Equipments.EquipmentNotFoundException;
import com.meeting.reservation.domain.reservation.Reservations.InvalidReservationPasswordException;
import com.meeting.reservation.domain.reservation.Reservations.ReservationNotFoundException;
import com.meeting.reservation.domain.room.MeetingRooms.MeetingRoomNotFoundException;
import com.meeting.reservation.persistence.InMemoryReservationRepository.MeetingRoomReservationsNotFoundException;
import com.meeting.reservation.persistence.InMemoryReservationRepository.NoReservationsForMeetingRoomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        log.warn("Exception : ", ex);

        return ResponseEntity.internalServerError()
                             .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                             .body(ex.getMessage());
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<String> handleReservationNotFoundException(ReservationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(ex.getMessage());
    }

    @ExceptionHandler(MeetingRoomNotFoundException.class)
    public ResponseEntity<String> handleMeetingRoomNotFoundException(MeetingRoomNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(ex.getMessage());
    }

    @ExceptionHandler(NoReservationsForMeetingRoomException.class)
    public ResponseEntity<String> handleNoReservationsForRoomException(
            NoReservationsForMeetingRoomException ex
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidReservationPasswordException.class)
    public ResponseEntity<String> handlerInvalidReservationPasswordException(InvalidReservationPasswordException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(ex.getMessage());
    }

    @ExceptionHandler(EquipmentNotFoundException.class)
    public ResponseEntity<String> handleEquipmentNotFoundException(EquipmentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(ex.getMessage());
    }

    @ExceptionHandler(MeetingRoomReservationsNotFoundException.class)
    public ResponseEntity<String> handleMeetingRoomReservationsNotFoundException(
            MeetingRoomReservationsNotFoundException ex
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {
        log.info("ex : ", ex);

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}
