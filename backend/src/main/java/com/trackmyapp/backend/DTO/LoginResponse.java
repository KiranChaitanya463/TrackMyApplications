package com.trackmyapp.backend.DTO;

import lombok.Getter;

@Getter
public class LoginResponse {

    private String token;
    private String username;

    public LoginResponse() {
    }

    public LoginResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }


}
