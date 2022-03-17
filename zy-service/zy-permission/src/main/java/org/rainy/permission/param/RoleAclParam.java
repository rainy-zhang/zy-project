package org.rainy.permission.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rainy.permission.entity.RoleAcl;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: wt1734
 * @date: 2021/11/11 0011 15:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleAclParam {

    @NotNull(message = "角色Id不能为空")
    private Integer roleId;
    private List<Integer> aclIds;

    public List<RoleAcl> convert() {
        return aclIds.stream().map(aclId -> RoleAcl.builder().aclId(aclId).roleId(roleId).build()).collect(Collectors.toList());
    }

}
