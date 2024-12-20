package com.rti.puppygram.service.impl;

import com.rti.puppygram.model.Post;
import com.rti.puppygram.repositories.PostRepository;
import com.rti.puppygram.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository repository;

    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Post create(Post post) {
        return repository.save(post);
    }

    @Override
    public ResponseEntity<?> likePost(String id, String userName) {
        Post post = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        String result;
        if(post.getLikes().contains(userName)){
            post.getLikes().remove(userName);
            result = "👍 removed";
        } else {
            post.getLikes().add(userName);
            result = "👍 added";
        }
        repository.save(post);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<Post>> getUserFeed(Long userId) {
        List<Post> feed = repository.findByUserIdOrderByDateDesc(String.valueOf(userId)).orElseThrow(() -> new EntityNotFoundException("Feed not found"));
        return ResponseEntity.ok(feed);
    }

    @Override
    public ResponseEntity<Post> getPostDetails(String id) {
        Post post = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        return ResponseEntity.ok(post);
    }

    @Override
    public Long getTotalPostsByUser(Long userId) {
        return repository.countByUserId(String.valueOf(userId));
    }

    @Override
    public Long getTotalPostsYouLiked(Long userId) {
        return repository.countTotalLikesByUserId(String.valueOf(userId));
    }

    @Override
    public Long getTotalOfLikesInYourPosts(Long userId) {
        return repository.countTotalLikesReceivedByUserId(String.valueOf(userId));
    }
}
