package com.rti.puppygram.repositories;

public interface CustomPostRepository {

    long countTotalLikesByUserId(String userId);
    long countTotalLikesReceivedByUserId(String userId);
}
