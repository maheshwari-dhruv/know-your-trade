package org.blog.knowyourtrade.domain.dto.response;

import lombok.*;
import org.blog.knowyourtrade.domain.dto.mapper.PostDTO;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogResponse {
    private List<PostDTO> postDTO;
}
