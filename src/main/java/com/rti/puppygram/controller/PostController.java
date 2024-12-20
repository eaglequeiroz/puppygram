package com.rti.puppygram.controller;

import com.rti.puppygram.model.Post;
import com.rti.puppygram.security.UserPrincipal;
import com.rti.puppygram.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        post.setDate(LocalDateTime.now());
        return ResponseEntity.ok(service.create(post));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> likePost(@PathVariable String id, Authentication authentication){
        String name = ((UserPrincipal) authentication.getPrincipal()).getUsername();
        return service.likePost(id, name);
    }

    @GetMapping("/{userId}/feed")
    public ResponseEntity<List<Post>> getUserFeed(@PathVariable Long userId){
        return service.getUserFeed(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostDetails(@PathVariable String id){
        return service.getPostDetails(id);
    }
}
