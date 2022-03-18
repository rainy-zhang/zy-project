package org.rainy.permission.service;

import lombok.extern.slf4j.Slf4j;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.exception.IllegalParamException;
import org.rainy.common.util.BeanValidator;
import org.rainy.permission.constant.LogOpType;
import org.rainy.permission.constant.LogType;
import org.rainy.permission.entity.Acl;
import org.rainy.permission.param.AclParam;
import org.rainy.permission.repository.AclRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AclService {

    private final AclRepository aclRepository;
    private final LogService logService;
    public AclService(AclRepository aclRepository, LogService logService) {
        this.aclRepository = aclRepository;
        this.logService = logService;
    }

    public List<Acl> findAll() {
        return aclRepository.findAll();
    }

    public List<Acl> findByModuleId(Integer aclModuleId) {
        return aclRepository.findByAclModuleId(aclModuleId);
    }

    public Acl findById(Integer id) {
        Optional<Acl> aclOptional = aclRepository.findById(id);
        return aclOptional.orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(AclParam aclParam) {
        BeanValidator.validate(aclParam, ValidateGroups.INSERT.class);
        if (aclRepository.existsByNameAndAclModuleId(aclParam.getName(), aclParam.getAclModuleId())) {
            throw new IllegalParamException("current aclModule acl name already in use");
        }
        Acl acl = aclParam.convert();
        acl = aclRepository.save(acl);

        logService.save(null, acl, LogType.ACL, LogOpType.INSERT);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(AclParam aclParam) {
        BeanValidator.validate(aclParam, ValidateGroups.UPDATE.class);
        if (aclRepository.existsByNameAndAclModuleId(aclParam.getName(), aclParam.getAclModuleId())) {
            throw new IllegalParamException("current aclModule acl name already in use");
        }
        Optional<Acl> originAclOptional = aclRepository.findById(aclParam.getId());
        Acl originAcl = originAclOptional.orElseThrow(() -> new BeanNotFoundException("before acl not found"));
        Acl acl = aclParam.convert();

        if (originAcl.equals(acl)) {
            log.warn("acl equals originAcl do nothing");
            return;
        }
        aclRepository.save(acl);
        logService.save(originAcl, acl, LogType.ACL, LogOpType.UPDATE);
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        Optional<Acl> originAclOptional = aclRepository.findById(id);
        Acl originAcl = originAclOptional.orElseThrow(() -> new BeanNotFoundException("acl not found"));
        aclRepository.deleteById(id);
        logService.save(originAcl, null, LogType.ACL, LogOpType.DELETE);
    }

}
