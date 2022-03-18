package org.rainy.permission.controller;

import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.permission.entity.User;
import org.rainy.permission.param.UserParam;
import org.rainy.permission.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 注册接口
     * @param userParam
     */
    @PostMapping(value = "/register")
    public void register(@RequestBody UserParam userParam) {
        userService.save(userParam);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public User findUser(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    /**
     * 查询用户分页信息
     * @param pageQuery
     * @return
     */
    @GetMapping(value = "/users")
    public PageResult<User> users(@RequestBody PageQuery pageQuery) {
        return userService.pageResult(pageQuery);
    }

    /**
     * 删除用户
     * @param id
     */
    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable(value = "id") Integer id) {
        userService.delete(id);
    }

}
