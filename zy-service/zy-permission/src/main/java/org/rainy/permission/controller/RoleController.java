package org.rainy.permission.controller;

import org.rainy.permission.dto.AclModuleDto;
import org.rainy.permission.dto.UserDto;
import org.rainy.permission.param.RoleAclParam;
import org.rainy.permission.param.RoleParam;
import org.rainy.permission.param.RoleUserParam;
import org.rainy.permission.service.RoleAclService;
import org.rainy.permission.service.RoleService;
import org.rainy.permission.service.RoleUserService;
import org.rainy.permission.service.TreeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: wt1734
 * @date: 2021/11/10 13:56
 */
@RestController
@RequestMapping(value = "/role")
public class RoleController {

    private final RoleService roleService;
    private final RoleUserService roleUserService;
    private final RoleAclService roleAclService;
    private final TreeService treeService;

    public RoleController(RoleService roleService, RoleUserService roleUserService, RoleAclService roleAclService, TreeService treeService) {
        this.roleService = roleService;
        this.roleUserService = roleUserService;
        this.roleAclService = roleAclService;
        this.treeService = treeService;
    }

    /**
     * 根据角色Id获取权限树
     * @param id
     * @return
     */
    @GetMapping(value = "/tree/{id}")
    public List<AclModuleDto> roleAclTree(@PathVariable(value = "id") Integer id) {
        return treeService.roleAclTree(id);
    }

    /**
     * 根据角色Id获取对应用户列表
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}/users")
    public List<UserDto> users(@PathVariable(value = "id") Integer id) {
        return roleUserService.findUserByRoleId(id);
    }

    /**
     * 保存角色
     * @param roleParam
     */
    @PostMapping(value = "/save")
    public void save(@RequestBody RoleParam roleParam) {
        roleService.save(roleParam);
    }

    /**
     * 删除角色
     * @param id
     */
    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable(value = "id") Integer id) {
        roleService.delete(id);
    }

    /**
     * 修改角色
     * @param roleParam
     */
    @PutMapping(value = "/update")
    public void update(@RequestBody RoleParam roleParam) {
        roleService.update(roleParam);
    }

    /**
     * 修改角色与权限关联关系
     * @param roleAclParam
     */
    @PutMapping(value = "/changeAcls")
    public void changeAcls(@RequestBody RoleAclParam roleAclParam) {
        roleAclService.changeRoleAcls(roleAclParam);
    }

    /**
     * 修改角色与用户关联关系
     * @param roleUserParam
     */
    @PutMapping(value = "/changeUsers")
    public void changeUsers(@RequestBody RoleUserParam roleUserParam) {
        roleUserService.changeRoleUsers(roleUserParam);
    }


}
