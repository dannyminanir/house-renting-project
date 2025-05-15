package com.example.houserenting.payloads;

import java.time.LocalDateTime;

public class AuthenticationResponse {
    public int status;
    public String token;
    public UserResponse data;
}
