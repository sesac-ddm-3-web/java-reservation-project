package com.example.ReserveSystem.Application;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ValidationService {
    public <T> void checkValid(@Valid T validationTarget){
        // @Valid 붙은 매서드 배개변수 유효성 검사

    }
}
