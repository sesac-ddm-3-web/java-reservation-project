package com.example.sesac_spring_practice_01.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {

    private final HttpStatus status;
    private String customErrorMessage;

    public BaseException(HttpStatus status) {
        this.status = status;
    }

    public BaseException(HttpStatus status, final String message) {
        this.status = status;
        this.customErrorMessage = message;
    }

    public boolean hasCustomMessage(){
        return customErrorMessage != null;
    }
}
