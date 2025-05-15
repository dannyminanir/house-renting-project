package com.example.houserenting.controllers;

import com.example.houserenting.payloads.BookingRequest;
import com.example.houserenting.payloads.BookingResponse;
import com.example.houserenting.services.BookingService;
import com.example.houserenting.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 *
 * @Nammes : Iradukunda Joselyne
 *
 * @registration : 21438/2023
 */
@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request) {
        BookingResponse booking = bookingService.createBooking(request);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 201);
        response.put("message", "Booking created successfully");
        response.put("data", booking);

        return ResponseEntity.status(201).body(response);
    }
    @GetMapping
    public ResponseEntity<?> getBookings(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Claims claims = jwtUtil.extractAllClaims(token);
        Integer tenantId = claims.get("id", Integer.class); // 👈 assuming you store 'id' in token

        List<BookingResponse> bookings = bookingService.getBookingsByTenant(tenantId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "User bookings fetched successfully");
        response.put("data", bookings);

        return ResponseEntity.ok(response);
    }


}
