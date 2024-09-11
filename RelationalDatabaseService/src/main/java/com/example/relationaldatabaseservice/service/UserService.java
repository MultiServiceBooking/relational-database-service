package com.example.relationaldatabaseservice.service;

import com.example.relationaldatabaseservice.dto.UserDto;
import com.example.relationaldatabaseservice.dto.UserGatewayDto;
import com.example.relationaldatabaseservice.model.User;
import com.example.relationaldatabaseservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return "User not found";
        } else if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Incorrect password";
        }
       /* else if (!password.equals(user.getPassword())) {
            return "Incorrect password";
        }*/
        return "Success";
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public boolean phoneNumberExists(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber) != null;
    }

    public void saveUser(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
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

    public UserDto findById(String id) {
        try {
            long longId = Long.parseLong(id);
            Optional<User> optionalUser = userRepository.findById(longId);

            if (optionalUser.isPresent()) {
                return convertToUserDto(optionalUser.get());
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDto.getName());
            user.setSurname(userDto.getSurname());
            user.setEmail(userDto.getEmail());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setRole(userDto.getRole());
            user.setHotelId(userDto.getHotelId());

            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                //user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                user.setPassword(userDto.getPassword());
            }

            User updatedUser = userRepository.save(user);
            return convertToUserDto(updatedUser);
        } else {
            return null;
        }
    }

    public String changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return "User not found";
        }
        if (!user.getPassword().equals(oldPassword)) {
            return "Incorrect old password";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        //user.setPassword(newPassword);
        userRepository.save(user);
        return "Password change successful";
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
        userDto.setHotelId(user.getHotelId());
        return userDto;
    }
}
