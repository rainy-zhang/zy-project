package org.rainy.blog.controller;

import org.rainy.blog.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/author")
public class AuthorController {

    @GetMapping
    public UserDto author() {
        // TODO: 通过调用permission服务，获取用户信息
        return UserDto.builder()
                .id(1)
                .nickname("Rainy Zhang")
                .email("rainy_zhang@foxmail.com")
                .description("a single life")
                .build();
    }

}
