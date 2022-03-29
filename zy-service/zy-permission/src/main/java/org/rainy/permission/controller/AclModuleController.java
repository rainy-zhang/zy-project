package org.rainy.permission.controller;

import org.rainy.permission.dto.AclModuleDto;
import org.rainy.permission.entity.AclModule;
import org.rainy.permission.param.AclModuleParam;
import org.rainy.permission.service.AclModuleService;
import org.rainy.permission.service.TreeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/aclModule")
public class AclModuleController {

    private final AclModuleService aclModuleService;
    private final TreeService treeService;

    public AclModuleController(AclModuleService aclModuleService, TreeService treeService) {
        this.aclModuleService = aclModuleService;
        this.treeService = treeService;
    }

    /**
     * 根据id获取权限模块信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/find/{id}")
    public AclModule findAclModule(@PathVariable("id") Integer id) {
        return aclModuleService.findById(id);
    }

    /**
     * 获取权限树
     *
     * @return
     */
    @GetMapping(value = "/acls")
    public List<AclModuleDto> aclTree() {
        return treeService.aclModuleTree();
    }

    /**
     * 保存权限模块
     *
     * @param aclModuleParam
     */
    @PostMapping(value = "/save")
    public void save(@RequestBody AclModuleParam aclModuleParam) {
        aclModuleService.save(aclModuleParam);
    }

    /**
     * 修改权限模块
     *
     * @param aclModuleParam
     */
    @PutMapping(value = "/update")
    public void update(@RequestBody AclModuleParam aclModuleParam) {
        aclModuleService.update(aclModuleParam);
    }

    /**
     * 删除权限模块
     *
     * @param id
     */
    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable(value = "id") Integer id) {
        aclModuleService.delete(id);
    }

}
