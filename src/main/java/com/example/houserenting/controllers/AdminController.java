package com.example.houserenting.controllers;

import com.example.houserenting.payloads.HouseResponse;
import com.example.houserenting.payloads.HouseStatusUpdateRequest;
import com.example.houserenting.payloads.RoleUpdateRequest;
import com.example.houserenting.payloads.UserResponse;
import com.example.houserenting.services.HouseService;
import com.example.houserenting.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private HouseService houseService;

    @PatchMapping("/users/{id}")
    public ResponseEntity<?> updateUserRole(@PathVariable Integer id,
                                            @RequestBody RoleUpdateRequest request,
                                            HttpServletRequest httpRequest) {
        UserResponse updated = userService.updateUserRole(id, request.role);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "User role updated successfully");
        response.put("data", updated);

        return ResponseEntity.ok(response);
    }
    @PatchMapping("/houses/{id}")
    public ResponseEntity<?> updateHouseStatus(@PathVariable Integer id,
                                               @RequestBody HouseStatusUpdateRequest request) {

        HouseResponse updated = houseService.updateHouseStatus(id, request.status);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "House status updated successfully");
        response.put("data", updated);

        return ResponseEntity.ok(response);
    }

}
