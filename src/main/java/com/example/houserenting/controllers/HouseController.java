package com.example.houserenting.controllers;

import com.example.houserenting.payloads.HouseRequest;
import com.example.houserenting.payloads.HouseResponse;
import com.example.houserenting.services.HouseService;
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
@RequestMapping("/api/v1/houses")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @PostMapping
    public ResponseEntity<?> createHouse(@RequestBody HouseRequest request) {
        HouseResponse response = houseService.createHouse(request);

        Map<String, Object> body = new HashMap<>();
        body.put("status", 201);
        body.put("message", "House created successfully");
        body.put("data", response);

        return ResponseEntity.status(201).body(body);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateHouse(@PathVariable Integer id,
                                         @RequestBody HouseRequest request,
                                         HttpServletRequest httpRequest) {
        String email = houseService.extractEmailFromToken(httpRequest);
        HouseResponse updatedHouse = houseService.updateHouse(id, request, email);

        Map<String, Object> body = new HashMap<>();
        body.put("status", 200);
        body.put("message", "House updated successfully");
        body.put("data", updatedHouse);

        return ResponseEntity.ok(body);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getHouseById(@PathVariable Integer id) {
        HouseResponse response = houseService.getHouseById(id);

        Map<String, Object> body = new HashMap<>();
        body.put("status", 200);
        body.put("message", "House fetched successfully");
        body.put("data", response);

        return ResponseEntity.ok(body);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHouse(@PathVariable Integer id, HttpServletRequest request) {
        String email = houseService.extractEmailFromToken(request);
        houseService.deleteHouse(id, email);

        Map<String, Object> data = new HashMap<>();
        data.put("message", "House deleted successfully");

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Deleted");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<?> getAllHouses() {
        List<HouseResponse> houseList = houseService.getAllHouses();

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "All houses fetched successfully");
        response.put("data", houseList);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/api/v1/houses")
    public ResponseEntity<?> getHouses(
            @RequestParam(required = false) Double minprice,
            @RequestParam(required = false) Double maxprice) {

        List<HouseResponse> houses;

        if (minprice == null && maxprice == null) {
            houses = houseService.getAllHouses();
        } else {
            houses = houseService.getHousesByPriceRange(minprice, maxprice);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Houses fetched successfully");
        response.put("data", houses);

        return ResponseEntity.ok(response);
    }

}
