package org.rainy.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rainy.project.entity.User;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserDto {

    private Integer id;

    private String username;

    private String email;

    private String nickname;

    private String description;

    private Integer status;

    private Integer seq;
    
    private String token;
    
    private Date expireAt;

    public static List<UserDto> converts(List<User> users) {
        return users.stream().map(UserDto::convert).collect(Collectors.toList());
    }

    public static UserDto convert(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}
