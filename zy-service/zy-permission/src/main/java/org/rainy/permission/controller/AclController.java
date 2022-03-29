package org.rainy.permission.controller;

import org.rainy.permission.entity.Acl;
import org.rainy.permission.param.AclParam;
import org.rainy.permission.service.AclService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/acl")
public class AclController {

    private final AclService aclService;

    public AclController(AclService aclService) {
        this.aclService = aclService;
    }

    /**
     * 根据id获取权限点信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/find/{id}")
    public Acl findAcl(@PathVariable(value = "id") Integer id) {
        return aclService.findById(id);
    }

    /**
     * 新增权限点
     *
     * @param aclParam
     */
    @PostMapping(value = "/save")
    public void save(@RequestBody AclParam aclParam) {
        aclService.save(aclParam);
    }

    /**
     * 修改权限点
     *
     * @param aclParam
     */
    @PutMapping(value = "/update")
    public void update(@RequestBody AclParam aclParam) {
        aclService.update(aclParam);
    }

    /**
     * 删除权限点
     *
     * @param id
     */
    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable(value = "id") Integer id) {
        aclService.delete(id);
    }


}
