package org.rainy.permission.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.rainy.permission.entity.AclModule;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: wt1734
 * @date: 2021/11/9 10:39
 */
@Data
@NoArgsConstructor
public class AclModuleDto extends AclModule {

    private List<AclDto> aclDtos;

    private List<AclModuleDto> aclModuleDtos;

    public static List<AclModuleDto> converts(List<AclModule> aclModules) {
        return aclModules.stream().map(AclModuleDto::convert).collect(Collectors.toList());
    }

    public static AclModuleDto convert(AclModule aclModule) {
        AclModuleDto aclModuleDto = new AclModuleDto();
        BeanUtils.copyProperties(aclModule, aclModuleDto);
        return aclModuleDto;
    }

}
