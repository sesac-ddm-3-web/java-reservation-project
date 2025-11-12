package SpringMiniProject.SeSAC.MeetingRoom.Management.Presentation;

import SpringMiniProject.SeSAC.MeetingRoom.Management.Domain.EmptyReservationListException;
import SpringMiniProject.SeSAC.MeetingRoom.Management.Domain.NoReservationExistsException;
import SpringMiniProject.SeSAC.MeetingRoom.Management.Domain.ReservationCollisionException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyReservationListException.class)
    public ResponseEntity<String> handleEmptyReservationListException(EmptyReservationListException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(NoReservationExistsException.class)
    public ResponseEntity<String> handleNoReservationExistsException(NoReservationExistsException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(ReservationCollisionException.class)
    public ResponseEntity<String> handleReservationCollisionException(ReservationCollisionException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("입력값이 유효하지 않습니다.");
        return ResponseEntity.badRequest().body(errorMessage);
    }

}
