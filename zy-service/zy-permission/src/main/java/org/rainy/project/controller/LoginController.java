package org.rainy.project.controller;

import org.rainy.project.dto.UserDto;
import org.rainy.project.param.LoginParam;
import org.rainy.project.service.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public UserDto login(@RequestBody LoginParam loginParam) {
        return loginService.login(loginParam);
    }

    /**
     * 退出登录
     */
    @GetMapping(value = "/logout")
    public void logout() {
        // TODO: 删除用户信息认证
        loginService.logout();
    }

}
