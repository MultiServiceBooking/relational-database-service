package com.example.relationaldatabaseservice.controller;

import com.example.relationaldatabaseservice.dto.UserDto;
import com.example.relationaldatabaseservice.dto.UserGatewayDto;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.relationaldatabaseservice.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserGatewayDto> getUserByUsername(@PathVariable String username) {
        UserGatewayDto userDto = userService.findUserByUsername(username);
        if (userDto != null) {
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<UserDto> getByUsername(@PathVariable String username) {
        UserDto userDto = userService.findByUsername(username);
        if(userDto != null) {
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable String id) {
        UserDto userDto = userService.findById(id);
        if(userDto != null) {
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
