package org.rainy.permission.controller;

import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.permission.entity.User;
import org.rainy.permission.param.UserParam;
import org.rainy.permission.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: wt1734
 * @date: 2021/11/8 11:43
 */
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
    @GetMapping(value = "/find/{id}")
    public User findUser(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    /**
     * 查询用户分页信息
     * @param pageQuery
     * @return
     */
    @GetMapping(value = "/findAll")
    public PageResult<User> pageList(@RequestBody PageQuery pageQuery) {
        return userService.findByPage(pageQuery);
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
