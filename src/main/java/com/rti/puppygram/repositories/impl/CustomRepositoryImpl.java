package com.rti.puppygram.repositories.impl;

import com.rti.puppygram.repositories.CustomPostRepository;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class CustomRepositoryImpl implements CustomPostRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public long countTotalLikesByUserId(String userId) {
        Aggregation aggregation = Aggregation.newAggregation(
                match(where("likes").in(userId)),
                group().count().as("totalLikes")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "posts", Document.class);
        return results.getUniqueMappedResult() != null ? results.getUniqueMappedResult().getLong("totalLikes") : 0;
    }

    @Override
    public long countTotalLikesReceivedByUserId(String userId) {
        Aggregation aggregation = Aggregation.newAggregation(
                match(where("userId").is(userId)),
                project().and("likes").size().as("likeCount"),
                group().sum("likeCount").as("totalLikesReceived")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "posts", Document.class);
        return results.getUniqueMappedResult() != null ? results.getUniqueMappedResult().getLong("totalLikesReceived") : 0;
    }
}
