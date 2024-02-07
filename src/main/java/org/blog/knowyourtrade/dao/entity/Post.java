package org.blog.knowyourtrade.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @Column(name = "post_id", nullable = false)
    private String postId;

    @Lob
    @Column(name = "post_title", columnDefinition = "TEXT", nullable = false)
    private String postTitle;

    @Lob
    @Column(name = "post_content", columnDefinition = "TEXT", nullable = false)
    private String postContent;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
