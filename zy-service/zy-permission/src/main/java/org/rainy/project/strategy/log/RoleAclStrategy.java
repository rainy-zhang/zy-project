package org.rainy.project.strategy.log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.rainy.project.beans.ApplicationContextHolder;
import org.rainy.project.exception.CommonException;
import org.rainy.project.exception.IllegalBeanException;
import org.rainy.project.util.JsonMapper;
import org.rainy.project.constant.LogType;
import org.rainy.project.entity.RoleAcl;
import org.rainy.project.param.RoleAclParam;
import org.rainy.project.repository.RoleAclRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RoleAclStrategy extends AbstractLogStrategy {

    private static final RoleAclStrategy instance = new RoleAclStrategy();
    private final RoleAclRepository roleAclRepository;

    private RoleAclStrategy() {
        this.roleAclRepository = ApplicationContextHolder.popBean(RoleAclRepository.class);
        register();
    }

    public static RoleAclStrategy getInstance() {
        return instance;
    }

    @Override
    public LogType type() {
        return LogType.ROLE_ACL;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recoveryUpdate(String beforeValue, String afterValue) {
        RoleAclParam beforeRoleAcl = JsonMapper.string2Object(beforeValue, new TypeReference<>() {
        });
        RoleAclParam afterRoleAcl = JsonMapper.string2Object(afterValue, new TypeReference<>() {
        });

        Preconditions.checkNotNull(beforeRoleAcl);
        Preconditions.checkNotNull(afterRoleAcl);

        Integer roleId = beforeRoleAcl.getRoleId();
        List<Integer> currentAclIds = roleAclRepository.findAclIdsByRoleId(roleId);

        if (!CollectionUtils.isEqualCollection(currentAclIds, afterRoleAcl.getAclIds())) {
            throw new IllegalBeanException("inconsistent with current aclIds information，try to operate another log");
        }

        List<RoleAcl> roleAclList = new RoleAclParam(roleId, beforeRoleAcl.getAclIds()).convert();
        roleAclRepository.deleteByRoleId(roleId);
        roleAclRepository.saveAll(roleAclList);
    }

    @Override
    public void recoveryDelete(String beforeUser) {
        throw new CommonException("can not invoke the method");
    }

    @Override
    public void recoveryInsert(String afterValue) {
        throw new CommonException("can not invoke the method");
    }

}
