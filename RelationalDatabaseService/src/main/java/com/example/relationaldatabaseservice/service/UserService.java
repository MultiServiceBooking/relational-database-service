package com.example.relationaldatabaseservice.service;

import com.example.relationaldatabaseservice.model.User;
import com.example.relationaldatabaseservice.repository.UserRepository;
import jakarta.transaction.Transactional;
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

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public boolean phoneNumberExists(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber) != null;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    @Transactional
    public void rollbackUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
