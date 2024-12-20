package com.rti.puppygram.model;

import lombok.Builder;

@Builder
public record UserProfile(
        String name,
        Long quantityOfPosts,
        Long quantityOfPostsYouLiked,
        Long quantityOfLikesInYourPosts) {
}
