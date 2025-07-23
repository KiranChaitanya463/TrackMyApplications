package com.trackmyapp.backend.DTO;

public class LoginResponse {
    private String message;

    public LoginResponse() {}

    public LoginResponse(String message) {
        this.message = message;
    }

    // Getter only
    public String getMessage() {
        return message;
    }
}
