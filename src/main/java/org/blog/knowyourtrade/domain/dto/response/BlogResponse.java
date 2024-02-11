package org.blog.knowyourtrade.domain.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogResponse {
    private String message;
    private int code;
    private String status;
}
