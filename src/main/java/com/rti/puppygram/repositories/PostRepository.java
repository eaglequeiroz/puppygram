package com.rti.puppygram.repositories;

import com.rti.puppygram.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, String>, CustomPostRepository {
    Optional<List<Post>> findByUserIdOrderByDateDesc(String userId);

    Long countByUserId(String userId);
}
