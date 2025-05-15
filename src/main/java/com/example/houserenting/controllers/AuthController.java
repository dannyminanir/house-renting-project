package com.example.houserenting.controllers;

import com.example.houserenting.payloads.AuthenticationRequest;
import com.example.houserenting.payloads.AuthenticationResponse;
import com.example.houserenting.payloads.UserRequest;
import com.example.houserenting.payloads.UserResponse;
import com.example.houserenting.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
/**
 *
 *
 * @Nammes : Iradukunda Joselyne
 *
 * @registration : 21438/2023
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest request) {
        UserResponse user = userService.registerUser(request);
        Map<String, Object> response = new HashMap<>();
        response.put("status", 201);
        response.put("data", user);
        return ResponseEntity.status(201).body(response);
    }
    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = userService.login(request.email, request.password);
        return ResponseEntity.ok(response);
    }

}
