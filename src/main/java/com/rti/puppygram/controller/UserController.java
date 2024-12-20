package com.rti.puppygram.controller;

import com.rti.puppygram.model.User;
import com.rti.puppygram.model.UserProfile;
import com.rti.puppygram.model.UserRecord;
import com.rti.puppygram.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody UserRecord user) {
        User createdUser = service.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRecord loginRequest) {
        return service.login(loginRequest);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable Long userId) {
        return service.getUserProfile(userId);
    }
}
