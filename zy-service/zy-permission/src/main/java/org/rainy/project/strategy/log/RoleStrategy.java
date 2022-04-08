package org.rainy.project.strategy.log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import org.rainy.project.exception.BeanNotFoundException;
import org.rainy.project.exception.IllegalBeanException;
import org.rainy.project.util.JsonMapper;
import org.rainy.project.constant.LogType;
import org.rainy.project.entity.Role;
import org.rainy.project.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RoleStrategy implements LogStrategy {

    private final RoleRepository roleRepository;

    public RoleStrategy(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public LogType type() {
        return LogType.ROLE;
    }

    @Override
    public void recoveryUpdate(String beforeValue, String afterValue) {
        Role beforeRole = JsonMapper.string2Object(beforeValue, new TypeReference<>() {
        });
        Role afterRole = JsonMapper.string2Object(afterValue, new TypeReference<>() {
        });

        Preconditions.checkNotNull(beforeRole);
        Preconditions.checkNotNull(afterRole);
        Preconditions.checkNotNull(beforeRole.getId());

        if (!roleRepository.existsById(beforeRole.getId())) {
            throw new IllegalBeanException("role not found");
        }
        if (roleRepository.existsByName(beforeRole.getName())) {
            throw new IllegalBeanException("role name already in use");
        }

        Role currentRole = roleRepository.findById(beforeRole.getId()).orElseThrow(() -> new BeanNotFoundException("role not found"));
        if (Objects.equals(currentRole, afterRole)) {
            throw new IllegalBeanException("inconsistent with current role information，try to operate another log");
        }

        roleRepository.save(beforeRole);
    }

    @Override
    public void recoveryDelete(String beforeValue) {
        Role beforeRole = JsonMapper.string2Object(beforeValue, new TypeReference<>() {
        });
        Preconditions.checkNotNull(beforeRole);
        Preconditions.checkNotNull(beforeRole.getId());
        if (roleRepository.existsById(beforeRole.getId())) {
            throw new IllegalBeanException("role primaryKey already in use");
        }
        if (roleRepository.existsByName(beforeRole.getName())) {
            throw new IllegalBeanException("role name already in use");
        }
        roleRepository.save(beforeRole);
    }

    @Override
    public void recoveryInsert(String afterValue) {
        Role afterRole = JsonMapper.string2Object(afterValue, new TypeReference<>() {
        });
        Preconditions.checkNotNull(afterRole);
        Role currentRole = roleRepository.findById(afterRole.getId()).orElseThrow(() -> new BeanNotFoundException("role not found"));

        if (Objects.equals(currentRole, afterRole)) {
            throw new IllegalBeanException("inconsistent with current role information，try to operate another log");
        }

        roleRepository.deleteById(afterRole.getId());
    }


}
