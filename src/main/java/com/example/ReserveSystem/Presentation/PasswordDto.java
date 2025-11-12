package com.example.ReserveSystem.Presentation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PasswordDto {
    @NotNull
    String password;

    public String getPassword() {
        return password;
    }
}
