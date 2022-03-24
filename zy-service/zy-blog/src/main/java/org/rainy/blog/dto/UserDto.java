package org.rainy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;

    private String username;

    private String nickname;

    private String email;

    private String description;

}
