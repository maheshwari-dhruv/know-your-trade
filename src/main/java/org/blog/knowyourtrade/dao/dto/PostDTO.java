package org.blog.knowyourtrade.dao.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String postId;
    private String postTitle;
    private String postContent;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
