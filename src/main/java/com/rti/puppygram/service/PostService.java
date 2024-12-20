package com.rti.puppygram.service;

import com.rti.puppygram.model.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
    Post create(Post post);

    ResponseEntity<?> likePost(String id, String name);

    ResponseEntity<List<Post>> getUserFeed(Long userId);

    ResponseEntity<Post> getPostDetails(String id);

    Long getTotalPostsByUser(Long userId);

    Long getTotalPostsYouLiked(Long userId);

    Long getTotalOfLikesInYourPosts(Long userId);
}
