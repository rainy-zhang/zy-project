package org.rainy.project.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.rainy.project.constant.ValidateGroups;
import org.rainy.project.exception.BeanNotFoundException;
import org.rainy.project.exception.IllegalParamException;
import org.rainy.project.util.BeanValidator;
import org.rainy.project.constant.LogOpType;
import org.rainy.project.constant.LogType;
import org.rainy.project.entity.Acl;
import org.rainy.project.entity.Role;
import org.rainy.project.param.RoleParam;
import org.rainy.project.repository.AclRepository;
import org.rainy.project.repository.RoleAclRepository;
import org.rainy.project.repository.RoleRepository;
import org.rainy.project.repository.RoleUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleUserRepository roleUserRepository;
    private final LogService logService;
    private final RoleAclRepository roleAclRepository;
    private final AclRepository aclRepository;

    public RoleService(RoleRepository roleRepository, RoleUserRepository roleUserRepository, LogService logService, RoleAclRepository roleAclRepository, AclRepository aclRepository) {
        this.roleRepository = roleRepository;
        this.roleUserRepository = roleUserRepository;
        this.logService = logService;
        this.roleAclRepository = roleAclRepository;
        this.aclRepository = aclRepository;
    }

    public List<Role> findRoleListByUserId(Integer userId) {
        List<Integer> roleIds = roleUserRepository.findRoleIdsByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return roleRepository.findAllById(roleIds);
    }

    public List<Acl> findAclsByIds(List<Integer> ids) {
        Preconditions.checkNotNull(ids, "角色ID列表不能为空");
        List<Integer> aclIds = roleAclRepository.findAclIdsByRoleIds(ids);
        if (CollectionUtils.isEmpty(aclIds)) {
            return Collections.emptyList();
        }
        return aclRepository.findAllById(aclIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(RoleParam roleParam) {
        BeanValidator.validate(roleParam, ValidateGroups.INSERT.class);
        if (roleRepository.existsByName(roleParam.getName())) {
            throw new IllegalParamException("role name already in use");
        }
        Role role = roleParam.convert();
        role = roleRepository.save(role);
        logService.save(null, role, LogType.ROLE, LogOpType.INSERT);
    }


    @Transactional(rollbackFor = Exception.class)
    public void update(RoleParam roleParam) {
        BeanValidator.validate(roleParam, ValidateGroups.UPDATE.class);
        if (roleRepository.existsByName(roleParam.getName())) {
            throw new IllegalParamException("role name already in use");
        }
        Optional<Role> originRoleOptional = roleRepository.findById(roleParam.getId());
        Role originRole = originRoleOptional.orElseThrow(() -> new BeanNotFoundException("before role not found"));
        Role role = roleParam.convert();
        if (role.equals(originRole)) {
            log.warn("role equals beforeRole do nothing");
            return;
        }
        roleRepository.save(role);
        logService.save(originRole, role, LogType.ROLE, LogOpType.UPDATE);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        Optional<Role> originRoleOptional = roleRepository.findById(id);
        Role originRole = originRoleOptional.orElseThrow(() -> new BeanNotFoundException("role not found"));
        roleRepository.deleteById(id);
        logService.save(originRole, null, LogType.ROLE, LogOpType.DELETE);
    }
}
