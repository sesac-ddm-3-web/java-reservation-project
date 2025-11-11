package com.example.sesac_spring_practice_01.global.exception.error;

import com.example.sesac_spring_practice_01.global.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ErrorResponse {

    @JsonProperty("message")
    private final String message;

    public static ErrorResponse generateFromCustomMessage(String message) {
        return new ErrorResponse(message);
    }

    public static ErrorResponse generateFrom(BaseException baseException) {
        if (baseException.hasCustomMessage()) {
            return new ErrorResponse(baseException.getCustomErrorMessage());}
        return new ErrorResponse(baseException.getMessage());
    }
}
