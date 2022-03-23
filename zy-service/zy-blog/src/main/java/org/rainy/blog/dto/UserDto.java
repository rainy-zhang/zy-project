package org.rainy.blog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private Integer id;

    private String username;

    private String email;

    private String telephone;

    private String remark;

    private Integer status;

    private Integer seq;

}
