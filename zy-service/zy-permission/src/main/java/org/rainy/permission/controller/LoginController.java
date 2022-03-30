package org.rainy.permission.controller;

import org.rainy.common.util.PasswordUtils;
import org.rainy.permission.entity.User;
import org.rainy.permission.exception.LoginException;
import org.rainy.permission.param.LoginParam;
import org.rainy.permission.service.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    /**
     * 登录接口
     *
     * @param loginParam
     */
    @PostMapping(value = "/login")
    public String login(@RequestBody LoginParam loginParam) {
        String token = loginService.login(loginParam);
        return token;
    }

    /**
     * 退出登录
     */
    @GetMapping(value = "/logout")
    public void logout() {
        // TODO: 删除用户信息认证
    }

}
