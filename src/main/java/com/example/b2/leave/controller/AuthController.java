package com.example.b2.leave.controller;

import com.example.b2.leave.entity.User;
import com.example.b2.leave.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("EMPLOYEE"); // Only default if not provided
        }
        return userRepo.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        System.out.println("Login Attempt: " + loginRequest.getEmail() + ", " + loginRequest.getPassword());
        System.out.println("Attempting login with email: " + loginRequest.getEmail() +
                ", password: " + loginRequest.getPassword());

        User user = userRepo.findByEmailAndPassword(
                loginRequest.getEmail(), loginRequest.getPassword()
        );

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        return ResponseEntity.ok(user);
    }
}