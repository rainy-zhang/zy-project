package org.rainy.permission.dto;

import lombok.Getter;
import lombok.Setter;
import org.rainy.permission.entity.Acl;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AclDto extends Acl {

    /**
     * 是否选中
     */
    private boolean checked;

    /**
     * 是否有权限操作
     */
    private boolean hasAcl;

    public static List<AclDto> converts(List<Acl> acls) {
        return acls.stream().map(AclDto::convert).collect(Collectors.toList());
    }

    public static AclDto convert(Acl acl) {
        AclDto aclDto = new AclDto();
        BeanUtils.copyProperties(acl, aclDto);
        return aclDto;
    }

}
