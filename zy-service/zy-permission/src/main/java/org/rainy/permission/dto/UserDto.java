package org.rainy.permission.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.rainy.permission.entity.User;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDto {

    private Integer id;

    private String username;

    private String email;

    private String telephone;

    private String description;

    private Integer status;

    private Integer seq;

    public static List<UserDto> converts(List<User> users) {
        return users.stream().map(UserDto::convert).collect(Collectors.toList());
    }

    public static UserDto convert(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}
