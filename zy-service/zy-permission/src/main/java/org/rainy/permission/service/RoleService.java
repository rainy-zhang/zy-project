package org.rainy.permission.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.exception.IllegalParamException;
import org.rainy.permission.constant.LogOpType;
import org.rainy.permission.constant.LogType;
import org.rainy.common.util.BeanValidator;
import org.rainy.permission.entity.Role;
import org.rainy.permission.param.RoleParam;
import org.rainy.permission.repository.RoleRepository;
import org.rainy.permission.repository.RoleUserRepository;
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

    public RoleService(RoleRepository roleRepository, RoleUserRepository roleUserRepository, LogService logService) {
        this.roleRepository = roleRepository;
        this.roleUserRepository = roleUserRepository;
        this.logService = logService;
    }

    public List<Role> findRoleListByUserId(Integer userId) {
        List<Integer> roleIds = roleUserRepository.findRoleIdsByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return roleRepository.findAllById(roleIds);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public List<Role> findByIds(List<Integer> ids) {
        return roleRepository.findAllById(ids);
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
