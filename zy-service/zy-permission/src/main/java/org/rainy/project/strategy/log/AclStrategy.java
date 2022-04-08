package org.rainy.project.strategy.log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import org.rainy.project.exception.BeanNotFoundException;
import org.rainy.project.exception.IllegalBeanException;
import org.rainy.project.util.JsonMapper;
import org.rainy.project.constant.LogType;
import org.rainy.project.entity.Acl;
import org.rainy.project.repository.AclRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Component
public class AclStrategy implements LogStrategy {

    private final AclRepository aclRepository;

    public AclStrategy(AclRepository aclRepository) {
        this.aclRepository = aclRepository;
    }

    @Override
    public LogType type() {
        return LogType.ACL;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recoveryUpdate(String beforeValue, String afterValue) {
        Acl afterAcl = JsonMapper.string2Object(afterValue, new TypeReference<>() {
        });
        Acl beforeAcl = JsonMapper.string2Object(beforeValue, new TypeReference<>() {
        });

        Preconditions.checkNotNull(afterAcl);
        Preconditions.checkNotNull(beforeAcl);

        if (aclRepository.existsByNameAndAclModuleId(beforeAcl.getName(), beforeAcl.getAclModuleId())) {
            throw new IllegalBeanException("current aclModule acl name already in use");
        }
        Optional<Acl> aclOptional = aclRepository.findById(beforeAcl.getId());
        Acl currentAcl = aclOptional.orElseThrow(() -> new BeanNotFoundException("acl not found"));
        if (!Objects.equals(currentAcl, afterAcl)) {
            throw new IllegalBeanException("inconsistent with current aclModule information，try to operate another log");
        }

        aclRepository.save(beforeAcl);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recoveryDelete(String beforeValue) {
        Acl beforeAcl = JsonMapper.string2Object(beforeValue, new TypeReference<>() {
        });
        Preconditions.checkNotNull(beforeAcl);
        Preconditions.checkNotNull(beforeAcl.getId());

        if (aclRepository.existsById(beforeAcl.getId())) {
            throw new IllegalBeanException("acl primaryKey already in use");
        }
        if (aclRepository.existsByNameAndAclModuleId(beforeAcl.getName(), beforeAcl.getAclModuleId())) {
            throw new IllegalBeanException("current aclModule acl name already in use");
        }
        aclRepository.save(beforeAcl);
    }

    @Override
    public void recoveryInsert(String afterValue) {
        Acl afterAcl = JsonMapper.string2Object(afterValue, new TypeReference<>() {
        });
        Preconditions.checkNotNull(afterAcl);
        Acl currentAcl = aclRepository.findById(afterAcl.getId()).orElseThrow(() -> new BeanNotFoundException("acl not found"));
        if (!Objects.equals(currentAcl, afterAcl)) {
            throw new IllegalBeanException("inconsistent with current acl information，try to operate another log");
        }

        aclRepository.deleteById(afterAcl.getId());
    }


}
