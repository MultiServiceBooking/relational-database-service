package com.example.relationaldatabaseservice.service;

import com.example.relationaldatabaseservice.model.User;
import com.example.relationaldatabaseservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return "User not found";
        } else if (!user.getPassword().equals(password)) {
            return "Incorrect password";
        }
        return "Success";
    }
}
