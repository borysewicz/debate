package com.example.Debate.dto.response;

import lombok.Value;

@Value
public class LoginResponse {
    private String token;
    private String role;

    public LoginResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }
}
