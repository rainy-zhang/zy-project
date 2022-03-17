package org.rainy.permission.strategy.log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.rainy.common.exception.IllegalBeanException;
import org.rainy.permission.constant.LogType;
import org.rainy.permission.entity.RoleUser;
import org.rainy.common.exception.CommonException;
import org.rainy.common.util.JsonMapper;
import org.rainy.permission.param.RoleUserParam;
import org.rainy.permission.repository.RoleUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: wt1734
 * @date: 2021/11/11 0011 15:02
 */
@Component
public class RoleUserStrategy implements LogStrategy {

    private final RoleUserRepository roleUserRepository;

    public RoleUserStrategy(RoleUserRepository roleUserRepository) {
        this.roleUserRepository = roleUserRepository;
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
