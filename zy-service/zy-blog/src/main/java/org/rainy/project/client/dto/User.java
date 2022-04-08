package org.rainy.project.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;

    private String username;

    private String nickname;

    private String email;

    private String description;

    private String avatar;

}
