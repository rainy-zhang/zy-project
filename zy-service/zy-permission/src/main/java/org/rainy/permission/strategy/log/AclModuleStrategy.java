package org.rainy.permission.strategy.log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.exception.IllegalBeanException;
import org.rainy.common.util.JsonMapper;
import org.rainy.permission.constant.LogType;
import org.rainy.permission.entity.AclModule;
import org.rainy.permission.repository.AclModuleRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Component
public class AclModuleStrategy implements LogStrategy {

    private final AclModuleRepository aclModuleRepository;

    public AclModuleStrategy(AclModuleRepository aclModuleRepository) {
        this.aclModuleRepository = aclModuleRepository;
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

