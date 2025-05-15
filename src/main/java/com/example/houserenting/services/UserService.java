package com.example.houserenting.services;

import com.example.houserenting.entities.User;
import com.example.houserenting.payloads.AuthenticationResponse;
import com.example.houserenting.payloads.UserRequest;
import com.example.houserenting.payloads.UserResponse;
import com.example.houserenting.repositories.UserRepository;
import com.example.houserenting.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserResponse registerUser(UserRequest request) {
        User user = new User();
        user.setFirstName(request.firstName);
        user.setLastName(request.lastName);
        user.setEmail(request.email);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setPhoneNumber(request.phoneNumber);
        user.setRole(request.role != null ? request.role : "TENANT");

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.id = savedUser.getId();
        response.firstName = savedUser.getFirstName();
        response.lastName = savedUser.getLastName();
        response.email = savedUser.getEmail();
        response.phoneNumber = savedUser.getPhoneNumber();
        response.role = savedUser.getRole();
        response.createdAt = savedUser.getCreatedAt();
        response.updatedAt = savedUser.getUpdatedAt();

        return response;
    }
    public AuthenticationResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(email);

        UserResponse userResponse = new UserResponse();
        userResponse.id = user.getId();
        userResponse.firstName = user.getFirstName();
        userResponse.lastName = user.getLastName();
        userResponse.email = user.getEmail();
        userResponse.phoneNumber = user.getPhoneNumber();
        userResponse.role = user.getRole();
        userResponse.createdAt = user.getCreatedAt();
        userResponse.updatedAt = user.getUpdatedAt();

        AuthenticationResponse response = new AuthenticationResponse();
        response.status = 200;
        response.token = token;
        response.data = userResponse;

        return response;
    }
    public UserResponse updateUserRole(Integer userId, String newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(newRole);
        user.setUpdatedAt(LocalDateTime.now());
        User updated = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.id = updated.getId();
        response.firstName = updated.getFirstName();
        response.lastName = updated.getLastName();
        response.email = updated.getEmail();
        response.phoneNumber = updated.getPhoneNumber();
        response.role = updated.getRole();
        response.createdAt = updated.getCreatedAt();
        response.updatedAt = updated.getUpdatedAt();

        return response;
    }
}
