package org.rainy.permission.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.rainy.common.constant.CommonStatus;
import org.rainy.permission.constant.LogOpType;
import org.rainy.permission.constant.LogType;
import org.rainy.permission.entity.Acl;
import org.rainy.permission.entity.Log;
import org.rainy.permission.entity.RoleAcl;
import org.rainy.common.util.BeanValidator;
import org.rainy.common.util.JsonMapper;
import org.rainy.permission.param.RoleAclParam;
import org.rainy.permission.repository.AclRepository;
import org.rainy.permission.repository.RoleAclRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleAclService {

    private final RoleAclRepository roleAclRepository;
    private final AclRepository aclRepository;
    private final LogService logService;

    public RoleAclService(RoleAclRepository roleAclRepository, AclRepository aclRepository, LogService logService) {
        this.roleAclRepository = roleAclRepository;
        this.aclRepository = aclRepository;
        this.logService = logService;
    }

    public List<Acl> findAclByRoleId(Integer roleId) {
        List<Integer> aclIds = roleAclRepository.findAclIdsByRoleId(roleId);
        if (CollectionUtils.isEmpty(aclIds)) {
            return Collections.emptyList();
        }
        return aclRepository.findAllById(aclIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public void changeRoleAcls(RoleAclParam roleAclParam) {
        BeanValidator.validate(roleAclParam);
        Integer roleId = roleAclParam.getRoleId();
        List<Integer> aclIds = roleAclParam.getAclIds();
        List<Integer> originAclIds = roleAclRepository.findAclIdsByRoleId(roleId);
        if (CollectionUtils.isNotEmpty(originAclIds)) {
            Set<Integer> originAclIdSet = new HashSet<>(originAclIds);
            Set<Integer> newAclIdSet = new HashSet<>(aclIds);
            newAclIdSet.removeAll(originAclIdSet);
            if (newAclIdSet.isEmpty()) {
                RoleAclService.log.warn("aclIds equals beforeAclIds do nothing");
                return;
            }
        }
        roleAclRepository.deleteByRoleId(roleId);
        List<RoleAcl> roleAclList = aclIds.stream().map(aclId -> RoleAcl.builder().aclId(aclId).roleId(roleId).build()).collect(Collectors.toList());
        roleAclRepository.saveAll(roleAclList);

        Log log = Log.builder()
                .targetId(roleId)
                .before(JsonMapper.object2String(originAclIds))
                .after(JsonMapper.object2String(aclIds))
                .status(CommonStatus.VALID.getCode())
                .type(LogType.ROLE_USER.getCode())
                .opType(LogOpType.UPDATE.getCode())
                .build();
        logService.save(log);
    }


}
