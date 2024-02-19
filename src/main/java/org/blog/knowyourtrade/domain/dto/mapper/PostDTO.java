package org.blog.knowyourtrade.domain.dto.mapper;

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
    private String createdAt;
    private String updatedAt;
}
