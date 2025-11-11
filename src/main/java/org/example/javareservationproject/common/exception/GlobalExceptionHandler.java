package org.example.javareservationproject.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String ERROR_FORMAT = "[ERROR] {}: {}";

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleException(BusinessException e) {
        log.error(ERROR_FORMAT, e.getClass().getSimpleName(), e.getMessage());
        e.printStackTrace();

        return ResponseEntity
            .status(e.getStatus())
            .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error(ERROR_FORMAT, e.getClass().getSimpleName(), e.getMessage());
        e.printStackTrace();

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("서버 내부 오류가 발생했습니다: " + e.getMessage());
    }
}
