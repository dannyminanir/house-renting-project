package com.example.houserenting.services;

import com.example.houserenting.entities.House;
import com.example.houserenting.entities.User;
import com.example.houserenting.payloads.HouseRequest;
import com.example.houserenting.payloads.HouseResponse;
import com.example.houserenting.payloads.UserResponse;
import com.example.houserenting.repositories.HouseRepository;
import com.example.houserenting.repositories.UserRepository;
import com.example.houserenting.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseService {

    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    public HouseResponse createHouse(HouseRequest request) {
        House house = new House();
        house.setTitle(request.title);
        house.setDescription(request.description);
        house.setLocation(request.location);
        house.setPrice(request.price);
        house.setRooms(request.rooms);
        house.setAvailable(request.available);
        house.setImageUrl(request.imageUrl);
        house.setStatus(request.status != null ? request.status : "pending");

        House saved = houseRepository.save(house);

        HouseResponse response = new HouseResponse();
        response.id = saved.getId();
        response.title = saved.getTitle();
        response.description = saved.getDescription();
        response.location = saved.getLocation();
        response.price = saved.getPrice();
        response.rooms = saved.getRooms();
        response.available = saved.getAvailable();
        response.imageUrl = saved.getImageUrl();
        response.landlordId = saved.getLandlordId();
        response.status = saved.getStatus();
        response.createdAt = saved.getCreatedAt();
        response.updatedAt = saved.getUpdatedAt();

        return response;
    }
    public String extractEmailFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader != null && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
        return jwtUtil.extractAllClaims(token).getSubject(); // subject = email
    }
    public HouseResponse updateHouse(Integer id, HouseRequest request, String email) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("House not found"));

        // Optional: Validate landlord's email if needed

        if (request.title != null) house.setTitle(request.title);
        if (request.description != null) house.setDescription(request.description);
        if (request.location != null) house.setLocation(request.location);
        if (request.price != null) house.setPrice(request.price);
        if (request.rooms != null) house.setRooms(request.rooms);
        if (request.available != null) house.setAvailable(request.available);
        if (request.imageUrl != null) house.setImageUrl(request.imageUrl);
        if (request.status != null) house.setStatus(request.status);

        House updated = houseRepository.save(house);

        HouseResponse response = new HouseResponse();
        response.id = updated.getId();
        response.title = updated.getTitle();
        response.description = updated.getDescription();
        response.location = updated.getLocation();
        response.price = updated.getPrice();
        response.rooms = updated.getRooms();
        response.available = updated.getAvailable();
        response.imageUrl = updated.getImageUrl();
        response.landlordId = updated.getLandlordId();
        response.status = updated.getStatus();
        response.createdAt = updated.getCreatedAt();
        response.updatedAt = updated.getUpdatedAt();

        return response;
    }
    public HouseResponse getHouseById(Integer id) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("House not found"));

        HouseResponse response = new HouseResponse();
        response.id = house.getId();
        response.title = house.getTitle();
        response.description = house.getDescription();
        response.location = house.getLocation();
        response.price = house.getPrice();
        response.rooms = house.getRooms();
        response.available = house.getAvailable();
        response.imageUrl = house.getImageUrl();
        response.landlordId = house.getLandlordId();
        response.status = house.getStatus();
        response.createdAt = house.getCreatedAt();
        response.updatedAt = house.getUpdatedAt();

        return response;
    }
    public void deleteHouse(Integer id, String email) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("House not found"));

        // Optional: Verify this landlord owns the house
        // If you're storing landlord email or need user lookup, add logic here

        houseRepository.delete(house);
    }
    public List<HouseResponse> getAllHouses() {
        List<House> houses = houseRepository.findAll();

        return houses.stream().map(h -> {
            HouseResponse res = new HouseResponse();
            res.id = h.getId();
            res.title = h.getTitle();
            res.description = h.getDescription();
            res.location = h.getLocation();
            res.price = h.getPrice();
            res.rooms = h.getRooms();
            res.available = h.getAvailable();
            res.imageUrl = h.getImageUrl();
            res.landlordId = h.getLandlordId();
            res.status = h.getStatus();
            res.createdAt = h.getCreatedAt();
            res.updatedAt = h.getUpdatedAt();
            return res;
        }).toList();
    }
    public UserResponse updateUserRole(Integer userId, String newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(newRole);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        UserResponse response = new UserResponse();
        response.id = user.getId();
        response.firstName = user.getFirstName();
        response.lastName = user.getLastName();
        response.email = user.getEmail();
        response.phoneNumber = user.getPhoneNumber();
        response.role = user.getRole();
        response.createdAt = user.getCreatedAt();
        response.updatedAt = user.getUpdatedAt();

        return response;
    }
    public HouseResponse updateHouseStatus(Integer houseId, String newStatus) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new RuntimeException("House not found"));

        house.setStatus(newStatus);
        house.setUpdatedAt(LocalDateTime.now());

        House updated = houseRepository.save(house);

        HouseResponse response = new HouseResponse();
        response.id = updated.getId();
        response.title = updated.getTitle();
        response.description = updated.getDescription();
        response.location = updated.getLocation();
        response.price = updated.getPrice();
        response.rooms = updated.getRooms();
        response.available = updated.getAvailable();
        response.imageUrl = updated.getImageUrl();
        response.landlordId = updated.getLandlordId();
        response.status = updated.getStatus();
        response.createdAt = updated.getCreatedAt();
        response.updatedAt = updated.getUpdatedAt();

        return response;
    }
    public List<HouseResponse> getHousesByPriceRange(Double minPrice, Double maxPrice) {
        List<House> houses = houseRepository.findByPriceRange(minPrice, maxPrice);

        return houses.stream().map(h -> {
            HouseResponse res = new HouseResponse();
            res.id = h.getId();
            res.title = h.getTitle();
            res.description = h.getDescription();
            res.location = h.getLocation();
            res.price = h.getPrice();
            res.rooms = h.getRooms();
            res.available = h.getAvailable();
            res.imageUrl = h.getImageUrl();
            res.landlordId = h.getLandlordId();
            res.status = h.getStatus();
            res.createdAt = h.getCreatedAt();
            res.updatedAt = h.getUpdatedAt();
            return res;
        }).toList();
    }

}
