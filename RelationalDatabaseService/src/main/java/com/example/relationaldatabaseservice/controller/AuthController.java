package com.example.relationaldatabaseservice.controller;

import com.example.relationaldatabaseservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.relationaldatabaseservice.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String email, @RequestParam String password) {
        String loginResult = userService.loginUser(email, password);
        Map<String, String> response = new HashMap<>();
        if ("User not found".equals(loginResult)) {
            response.put("error", "User not found");
            return ResponseEntity.status(404).body(response);
        } else if ("Incorrect password".equals(loginResult)) {
            response.put("error", "Incorrect password");
            return ResponseEntity.status(401).body(response);
        }
        response.put("message", "Login successful");
        return ResponseEntity.ok(response);
    }

}