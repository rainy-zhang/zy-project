package org.rainy.project.service;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.rainy.project.dto.AclDto;
import org.rainy.project.dto.AclModuleDto;
import org.rainy.project.entity.Acl;
import org.rainy.project.entity.AclModule;
import org.rainy.project.repository.AclModuleRepository;
import org.rainy.project.repository.AclRepository;
import org.rainy.project.repository.RoleAclRepository;
import org.rainy.project.repository.RoleUserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TreeService {

    private final RoleAclRepository roleAclRepository;
    private final AclRepository aclRepository;
    private final AclModuleRepository aclModuleRepository;
    private final RoleUserRepository roleUserRepository;

    public TreeService(RoleAclRepository roleAclRepository, AclRepository aclRepository, AclModuleRepository aclModuleRepository, RoleUserRepository roleUserRepository) {
        this.roleAclRepository = roleAclRepository;
        this.aclRepository = aclRepository;
        this.aclModuleRepository = aclModuleRepository;
        this.roleUserRepository = roleUserRepository;
    }

    /**
     * 根据用户Id获取对应的权限树
     *
     * @param userId
     * @return
     */
    public List<AclModuleDto> userAclTree(Integer userId) {
        List<Integer> roleIds = roleUserRepository.findRoleIdsByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        List<Integer> aclIds = roleAclRepository.findAclIdsByRoleIds(roleIds);
        return aclModuleTree(aclIds);
    }

    /**
     * 根据角色Id获取权限树
     *
     * @param roleId
     * @return {@link List<AclModuleDto>}
     */
    public List<AclModuleDto> roleAclTree(Integer roleId) {
        List<Integer> aclIds = roleAclRepository.findAclIdsByRoleId(roleId);
        return aclModuleTree(aclIds);
    }

    /**
     * 获取权限树
     *
     * @return
     */
    public List<AclModuleDto> aclModuleTree() {
        return aclModuleTree(null);
    }

    private List<AclModuleDto> aclModuleTree(List<Integer> aclIds) {
        List<Acl> acls = aclRepository.findAll();
        List<AclDto> aclDtos = AclDto.converts(acls);
        if (CollectionUtils.isNotEmpty(aclIds)) {
            aclDtos.forEach(aclDto -> {
                if (aclIds.contains(aclDto.getId())) {
                    aclDto.setChecked(true);
                    aclDto.setHasAcl(true);
                }
            });
        }

        List<AclModule> aclModules = aclModuleRepository.findAll();
        List<AclModuleDto> aclModuleDtos = AclModuleDto.converts(aclModules);

        Map<Integer, List<AclModuleDto>> aclModuleDtoMap = aclModuleDtos.stream().collect(Collectors.groupingBy(AclModule::getParentId));
        Map<Integer, List<AclDto>> aclDtoMap = aclDtos.stream().collect(Collectors.groupingBy(AclDto::getAclModuleId));

        List<AclModuleDto> rootAclModuleDto = aclModuleDtoMap.get(-1);
        List<AclModuleDto> aclModuleTree = Lists.newArrayList();

        transformAclModuleTree(rootAclModuleDto, aclModuleDtoMap, aclDtoMap, aclModuleTree);
        return aclModuleTree;
    }

    /**
     * @param rootAclModuleDto 当前要处理的权限模块
     * @param aclModuleDtoMap  key -> parentId, value -> {@link List<AclModuleDto>}
     * @param aclDtoMap        key -> aclModuleId, value {@link List<AclDto>}
     * @param aclModuleTree    权限树结果
     */
    private void transformAclModuleTree(List<AclModuleDto> rootAclModuleDto, Map<Integer, List<AclModuleDto>> aclModuleDtoMap, Map<Integer, List<AclDto>> aclDtoMap, List<AclModuleDto> aclModuleTree) {
        for (AclModuleDto aclModuleDto : rootAclModuleDto) {
            // 获取当前权限模块下所有权限点
            List<AclDto> aclDtos = aclDtoMap.get(aclModuleDto.getId());
            // 获取当前权限模块下所有权限模块
            List<AclModuleDto> aclModuleDtos = aclModuleDtoMap.get(aclModuleDto.getId());

            aclModuleDto.setAclDtos(aclDtos);
            aclModuleDto.setAclModuleDtos(aclModuleDtos);

            transformAclModuleTree(aclModuleDtos, aclModuleDtoMap, aclDtoMap, aclModuleTree);
        }
    }

}
