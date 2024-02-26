package org.blog.knowyourtrade.domain.dto.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PostRequest {
    private String title;
    private String content;
    private String category;
}
