package org.rainy.permission.controller;

import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.permission.dto.UserDto;
import org.rainy.permission.param.UserParam;
import org.rainy.permission.param.VisitorParam;
import org.rainy.permission.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 注册接口
     *
     * @param userParam
     */
    @PostMapping(value = "/register")
    public UserDto register(@RequestBody UserParam userParam) throws IOException {
        return userService.register(userParam);
    }

    /**
     * 保存游客信息
     *
     * @param visitorParam
     * @throws IOException
     */
    @PostMapping(value = "/visitor/register")
    public UserDto saveVisitor(@RequestBody VisitorParam visitorParam) throws IOException {
        return userService.saveVisitor(visitorParam);
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public UserDto findUser(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    /**
     * 查询用户分页信息
     *
     * @param pageQuery
     * @return
     */
    @GetMapping(value = "/users")
    public PageResult<UserDto> users(@RequestBody PageQuery pageQuery) {
        return userService.userPage(pageQuery);
    }

    /**
     * 删除用户
     *
     * @param id
     */
    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable(value = "id") Integer id) {
        userService.delete(id);
    }

}
