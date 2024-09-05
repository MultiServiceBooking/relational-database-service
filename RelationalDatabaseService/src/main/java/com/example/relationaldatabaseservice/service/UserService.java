package com.example.relationaldatabaseservice.service;

import com.example.relationaldatabaseservice.dto.UserDto;
import com.example.relationaldatabaseservice.dto.UserGatewayDto;
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

    public UserGatewayDto findUserByUsername(String username) {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            return new UserGatewayDto(user.getEmail(), user.getPassword(), user.getRole().name());
        } else {
            return null;
        }
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            return convertToUserDto(user);
        } else {
            return null;
        }
    }

    private UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setRole(user.getRole());
        return userDto;
    }
}
