package spring_junyeong.__meetingRoom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring_junyeong.__meetingRoom.domain.error.ErrorResponse;
import spring_junyeong.__meetingRoom.domain.error.ReservationError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ReservationError 및 이를 상속받는 모든 예외(NotFound, Capacity, TimeOverlap 등)를 처리
    @ExceptionHandler(ReservationError.class)
    public ResponseEntity<ErrorResponse> handleReservationError(ReservationError e) {

        // 1. HTTP 상태 코드 추출: ReservationError에 정의된 status 필드 사용
        HttpStatus status = HttpStatus.valueOf(e.getStatusCode());

        // 2. 응답 객체 생성 및 반환
        // 클라이언트에게 에러 메시지와 함께 정의된 HTTP 상태 코드를 반환합니다.
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage()),
                status
        );
    }

    // Spring의 Validation 예외(jakarta.validation.Valid 실패 시) 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(
                new ErrorResponse(errorMessage),
                HttpStatus.BAD_REQUEST
        );
    }
}
