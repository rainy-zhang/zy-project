package org.rainy.permission.controller;

import org.rainy.permission.entity.User;
import org.rainy.common.util.BeanValidator;
import org.rainy.common.util.PasswordUtils;
import org.rainy.permission.exception.LoginException;
import org.rainy.permission.param.LoginParam;
import org.rainy.permission.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录接口
     * @param loginParam
     */
    @PostMapping(value = "/login")
    public void login(@RequestBody LoginParam loginParam) {
        BeanValidator.validate(loginParam);
        Optional<User> userOptional = userService.findByKeyword(loginParam.getUsername());
        User user = userOptional.orElseThrow(() -> new LoginException("username or password error"));

        String encryptPassword = PasswordUtils.encrypt(loginParam.getPassword());
        if (Objects.equals(user.getPassword(), encryptPassword)) {
            throw new LoginException("username or password error");
        }

        // TODO: 存储用户信息认证
    }

    /**
     * 退出登录
     */
    @GetMapping(value = "/logout")
    public void logout() {
        // TODO: 删除用户信息认证
    }

}
