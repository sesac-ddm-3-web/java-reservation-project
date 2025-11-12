package com.jiwoo.MeetingRoom.domain;

import jakarta.validation.constraints.NotBlank;

public class GuestInfo {

    @NotBlank(message = "이름은 필수 입니다.")
    private String name;
    @NotBlank(message = "비밀번호는 필수 입니다.")
    private String password;
    @NotBlank(message = "전화번호는 필수 입니다.")
    private String phone;

    public GuestInfo() {
    }

    public GuestInfo(String name, String password, String phone) {
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }
}