package org.rainy.project.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.rainy.project.constant.CommonStatus;
import org.rainy.project.util.BeanValidator;
import org.rainy.project.util.JsonMapper;
import org.rainy.project.constant.LogOpType;
import org.rainy.project.constant.LogType;
import org.rainy.project.dto.UserDto;
import org.rainy.project.entity.Log;
import org.rainy.project.entity.RoleUser;
import org.rainy.project.param.RoleUserParam;
import org.rainy.project.repository.RoleUserRepository;
import org.rainy.project.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleUserService {

    private final RoleUserRepository roleUserRepository;
    private final UserRepository userRepository;
    private final LogService logService;

    public RoleUserService(RoleUserRepository roleUserRepository, UserRepository userRepository, LogService logService) {
        this.roleUserRepository = roleUserRepository;
        this.userRepository = userRepository;
        this.logService = logService;
    }

    public List<UserDto> findUserByRoleId(Integer roleId) {
        List<Integer> userIds = roleUserRepository.findUserIdsByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        return UserDto.converts(userRepository.findAllById(userIds));
    }

    @Transactional(rollbackFor = Exception.class)
    public void changeRoleUsers(RoleUserParam roleUserParam) {
        BeanValidator.validate(roleUserParam);
        Integer roleId = roleUserParam.getRoleId();
        List<Integer> userIds = roleUserParam.getUserIds();
        List<Integer> originUserIds = roleUserRepository.findUserIdsByRoleId(roleId);
        if (CollectionUtils.isNotEmpty(originUserIds)) {
            Set<Integer> originUserIdSet = new HashSet<>(originUserIds);
            Set<Integer> newUserIdSet = new HashSet<>(userIds);
            newUserIdSet.removeAll(originUserIdSet);
            if (newUserIdSet.isEmpty()) {
                RoleUserService.log.warn("userIds equals beforeUserIds do nothing");
                return;
            }
        }
        roleUserRepository.deleteByRoleId(roleId);
        List<RoleUser> roleUserList = userIds.stream().map(userId -> RoleUser.builder().userId(userId).roleId(roleId).build()).collect(Collectors.toList());
        roleUserRepository.saveAll(roleUserList);

        Log log = Log.builder()
                .targetId(roleId)
                .before(JsonMapper.object2String(originUserIds))
                .after(JsonMapper.object2String(userIds))
                .status(CommonStatus.VALID.getCode())
                .type(LogType.ROLE_USER.getCode())
                .opType(LogOpType.UPDATE.getCode())
                .build();
        logService.save(log);
    }


}
