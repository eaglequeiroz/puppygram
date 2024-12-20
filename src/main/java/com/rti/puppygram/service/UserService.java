package com.rti.puppygram.service;

import com.rti.puppygram.model.User;
import com.rti.puppygram.model.UserProfile;
import com.rti.puppygram.model.UserRecord;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User createUser(UserRecord user);
    ResponseEntity<?> login(UserRecord loginRequest);
    ResponseEntity<UserProfile> getUserProfile(Long userId);
}
