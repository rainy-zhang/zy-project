package org.rainy.project.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rainy.project.constant.ValidateGroups;
import org.rainy.project.entity.RoleAcl;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleAclParam {

    @NotNull(message = "角色Id不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private Integer roleId;
    private List<Integer> aclIds;

    public List<RoleAcl> convert() {
        return aclIds.stream().map(aclId -> RoleAcl.builder().aclId(aclId).roleId(roleId).build()).collect(Collectors.toList());
    }

}
