package com.rti.puppygram.service.impl;

import com.rti.puppygram.model.User;
import com.rti.puppygram.model.UserProfile;
import com.rti.puppygram.model.UserRecord;
import com.rti.puppygram.repositories.UserRepository;
import com.rti.puppygram.service.PostService;
import com.rti.puppygram.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final PostService postService;
    private final UserRepository repository;

    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    public UserServiceImpl(PostService postService, UserRepository repository) {
        this.postService = postService;
        this.repository = repository;
    }


    @Override
    public User createUser(UserRecord user) {
        repository.findByUsername(user.name()).ifPresent(data -> {
            throw new EntityExistsException("It was not possible to register this user");
        });
        User newUser = User.builder()
                .email(user.email())
                .username(user.name())
                .password(encoder().encode(user.password()))
                .roles(user.roles())
                .build();
        return repository.save(newUser);
    }

    @Override
    public ResponseEntity<?> login(UserRecord loginRequest) {
        Optional<User> user = repository.findByUsername(loginRequest.name());
        if (user.isPresent() && encoder().matches(loginRequest.password(), user.get().getPassword())) {
            return ResponseEntity.ok("Login successful!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized attempt to login");
    }

    @Override
    public ResponseEntity<UserProfile> getUserProfile(Long userId) {
        User user = repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        Long quantityOfPosts = postService.getTotalPostsByUser(user.getId());
        Long quantityOfPostsYouLiked = postService.getTotalPostsYouLiked(user.getId());
        Long quantityOfLikesInYourPosts = postService.getTotalOfLikesInYourPosts(user.getId());

        UserProfile userProfile = UserProfile.builder()
                .name(user.getUsername())
                .quantityOfPosts(quantityOfPosts)
                .quantityOfLikesInYourPosts(quantityOfLikesInYourPosts)
                .quantityOfPostsYouLiked(quantityOfPostsYouLiked)
                .build();
        return ResponseEntity.ok(userProfile);
    }
}
