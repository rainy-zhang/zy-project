package org.rainy.project.strategy.log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.rainy.project.beans.ApplicationContextHolder;
import org.rainy.project.constant.LogType;
import org.rainy.project.entity.RoleUser;
import org.rainy.project.exception.CommonException;
import org.rainy.project.exception.IllegalBeanException;
import org.rainy.project.param.RoleUserParam;
import org.rainy.project.repository.RoleUserRepository;
import org.rainy.project.util.JsonMapper;

import java.util.List;

public class RoleUserStrategy extends AbstractLogStrategy {

    private static final RoleUserStrategy instance = new RoleUserStrategy();
    private final RoleUserRepository roleUserRepository;

    private RoleUserStrategy() {
        this.roleUserRepository = ApplicationContextHolder.popBean(RoleUserRepository.class);
        register();
    }

    public static RoleUserStrategy getInstance() {
        return instance;
    }

    @Override
    public LogType type() {
        return LogType.ROLE_USER;
    }

    @Override
    public void recoveryUpdate(String beforeValue, String afterValue) {
        RoleUserParam beforeRoleUser = JsonMapper.string2Object(beforeValue, new TypeReference<>() {
        });
        RoleUserParam afterRoleUser = JsonMapper.string2Object(afterValue, new TypeReference<>() {
        });
        Preconditions.checkNotNull(beforeRoleUser);
        Preconditions.checkNotNull(afterRoleUser);

        Integer roleId = beforeRoleUser.getRoleId();
        List<Integer> currentUserIds = roleUserRepository.findUserIdsByRoleId(roleId);

        if (!CollectionUtils.isEqualCollection(currentUserIds, afterRoleUser.getUserIds())) {
            throw new IllegalBeanException("inconsistent with current userIds information，try to operate another log");
        }

        List<Integer> userIds = beforeRoleUser.getUserIds();
        roleUserRepository.deleteByRoleId(roleId);
        List<RoleUser> roleUsers = new RoleUserParam(roleId, userIds).convert();
        roleUserRepository.saveAll(roleUsers);
    }

    @Override
    public void recoveryDelete(String beforeValue) {
        throw new CommonException("can not invoke the method");
    }

    @Override
    public void recoveryInsert(String afterValue) {
        throw new CommonException("can not invoke the method");
    }


}
