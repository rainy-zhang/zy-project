package org.rainy.permission.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.exception.IllegalParamException;
import org.rainy.common.util.BeanValidator;
import org.rainy.permission.constant.LogOpType;
import org.rainy.permission.constant.LogType;
import org.rainy.permission.entity.AclModule;
import org.rainy.permission.param.AclModuleParam;
import org.rainy.permission.repository.AclModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @description:
 * @author: wt1734
 * @date: 2021/11/9 16:30
 */
@Service
@Slf4j
public class AclModuleService {

    private final AclModuleRepository aclModuleRepository;
    private final LogService logService;

    public AclModuleService(AclModuleRepository aclModuleRepository, LogService logService) {
        this.aclModuleRepository = aclModuleRepository;
        this.logService = logService;
    }

    public AclModule findById(Integer id) {
        Preconditions.checkNotNull(id);
        return aclModuleRepository.findById(id).orElseThrow(() -> new BeanNotFoundException("user not found"));
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(AclModuleParam aclModuleParam) {
        BeanValidator.validate(aclModuleParam, ValidateGroups.INSERT.class);
        if (aclModuleRepository.existsByName(aclModuleParam.getName())) {
            throw new IllegalParamException("acl module name already in use");
        }
        AclModule aclModule = aclModuleParam.convert();
        aclModule = aclModuleRepository.save(aclModule);
        logService.save(null, aclModule, LogType.ACL_MODULE, LogOpType.INSERT);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(AclModuleParam aclModuleParam) {
        BeanValidator.validate(aclModuleParam, ValidateGroups.UPDATE.class);
        if (aclModuleRepository.existsByName(aclModuleParam.getName())) {
            throw new IllegalParamException("acl module name already in use");
        }
        Optional<AclModule> originAclModuleOptional = aclModuleRepository.findById(aclModuleParam.getId());
        AclModule originAclModule = originAclModuleOptional.orElseThrow(() -> new BeanNotFoundException("before aclModule not found"));
        AclModule aclModule = aclModuleParam.convert();
        if (aclModule.equals(originAclModule)) {
            log.warn("aclModule equals originAclModule do nothing");
            return;
        }

        aclModuleRepository.save(aclModule);
        logService.save(originAclModule, aclModule, LogType.ACL_MODULE, LogOpType.UPDATE);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        Optional<AclModule> originAclModuleOptional = aclModuleRepository.findById(id);
        AclModule originAclModule = originAclModuleOptional.orElseThrow(() -> new BeanNotFoundException("aclModule not found"));
        aclModuleRepository.deleteById(id);
        logService.save(originAclModule, null, LogType.ACL_MODULE, LogOpType.DELETE);
    }


}

