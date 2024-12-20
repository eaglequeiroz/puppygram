package com.rti.puppygram.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "posts")
@Getter @Setter
@NoArgsConstructor
public class Post {

    private String id;
    private String userId;
    private String content;
    private String imageUrl;
    private LocalDateTime date;
    private Set<String> likes = new HashSet<>();
}
