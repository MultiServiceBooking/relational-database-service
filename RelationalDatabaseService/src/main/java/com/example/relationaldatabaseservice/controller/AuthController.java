package com.example.relationaldatabaseservice.controller;

import com.example.relationaldatabaseservice.enums.UserRole;
import com.example.relationaldatabaseservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.relationaldatabaseservice.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        String loginResult = userService.loginUser(email, password);
        Map<String, String> response = new HashMap<>();
        if ("User not found".equals(loginResult)) {
            response.put("error", "User with this email address does not exist.");
            return ResponseEntity.status(404).body(response);
        } else if ("Incorrect password".equals(loginResult)) {
            response.put("error", "Incorrect password.");
            return ResponseEntity.status(401).body(response);
        }
        response.put("message", "Login successful");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();

        if (userService.emailExists(user.getEmail())) {
            response.put("error", "Email already in use");
            return ResponseEntity.status(409).body(response);
        }

        if (userService.phoneNumberExists(user.getPhoneNumber())) {
            response.put("error", "Phone number already in use");
            return ResponseEntity.status(409).body(response);
        }

        user.setRole(UserRole.GUEST);
        user.setPassword(user.getPassword());
        userService.saveUser(user);

        response.put("message", "User registered successfully");
        return ResponseEntity.status(201).body(response);
    }
    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody Map<String, String> passwordData) {
        String email = passwordData.get("email");
        String oldPassword = passwordData.get("oldPassword");
        String newPassword = passwordData.get("newPassword");

        String result = userService.changePassword(email, oldPassword, newPassword);
        Map<String, String> response = new HashMap<>();

        if ("User not found".equals(result)) {
            response.put("error", "User with this email address does not exist.");
            return ResponseEntity.status(404).body(response);
        } else if ("Incorrect old password".equals(result)) {
            response.put("error", "Incorrect old password.");
            return ResponseEntity.status(401).body(response);
        } else if ("Password change successful".equals(result)) {
            response.put("message", "Password changed successfully.");
            return ResponseEntity.ok(response);
        }

        response.put("error", "An unexpected error occurred.");
        return ResponseEntity.status(500).body(response);
    }


}