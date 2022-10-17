package org.rainy.project.strategy.log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import org.rainy.project.beans.ApplicationContextHolder;
import org.rainy.project.exception.BeanNotFoundException;
import org.rainy.project.exception.IllegalBeanException;
import org.rainy.project.util.JsonMapper;
import org.rainy.project.constant.LogType;
import org.rainy.project.entity.AclModule;
import org.rainy.project.repository.AclModuleRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

public class AclModuleStrategy extends AbstractLogStrategy {

    private static final AclModuleStrategy instance = new AclModuleStrategy();
    private final AclModuleRepository aclModuleRepository;

    private AclModuleStrategy() {
        register();
        this.aclModuleRepository = ApplicationContextHolder.popBean(AclModuleRepository.class);
    }

    public static AclModuleStrategy getInstance() {
        return instance;
    }

    @Override
    public LogType type() {
        return LogType.ACL_MODULE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recoveryUpdate(String beforeValue, String afterValue) {
        AclModule afterAclModule = JsonMapper.string2Object(afterValue, new TypeReference<>() {
        });
        AclModule beforeAclModule = JsonMapper.string2Object(beforeValue, new TypeReference<>() {
        });
        Preconditions.checkNotNull(afterAclModule);
        Preconditions.checkNotNull(beforeAclModule);

        if (aclModuleRepository.existsByName(beforeAclModule.getName())) {
            throw new IllegalBeanException("acl module name already in use");
        }

        Optional<AclModule> aclModuleOptional = aclModuleRepository.findById(beforeAclModule.getId());
        AclModule currentAclModule = aclModuleOptional.orElseThrow(() -> new BeanNotFoundException("aclModule not found"));

        if (!Objects.equals(currentAclModule, afterAclModule)) {
            throw new IllegalBeanException("inconsistent with current aclModule information，try to operate another log");
        }
        aclModuleRepository.save(beforeAclModule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recoveryDelete(String beforeValue) {
        AclModule beforeAclModule = JsonMapper.string2Object(beforeValue, new TypeReference<>() {
        });
        Preconditions.checkNotNull(beforeAclModule);
        Preconditions.checkNotNull(beforeAclModule.getId());

        if (aclModuleRepository.existsById(beforeAclModule.getId())) {
            throw new IllegalBeanException("aclModule primaryKey already in use");
        }
        if (aclModuleRepository.existsByName(beforeAclModule.getName())) {
            throw new IllegalBeanException("aclModule name already in use");
        }
        aclModuleRepository.save(beforeAclModule);
    }

    @Override
    public void recoveryInsert(String afterValue) {
        AclModule afterAclModule = JsonMapper.string2Object(afterValue, new TypeReference<>() {
        });

        Preconditions.checkNotNull(afterAclModule);
        Optional<AclModule> aclModuleOptional = aclModuleRepository.findById(afterAclModule.getId());
        AclModule currentAclModule = aclModuleOptional.orElseThrow(() -> new BeanNotFoundException("aclModule not found"));

        if (!Objects.equals(currentAclModule, afterAclModule)) {
            throw new IllegalBeanException("inconsistent with current aclModule information，try to operate another log");
        }
        aclModuleRepository.deleteById(afterAclModule.getId());
    }

}

