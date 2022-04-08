package org.rainy.project.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rainy.project.constant.ValidateGroups;
import org.rainy.project.entity.RoleUser;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleUserParam {

    @NotNull(message = "角色Id不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private Integer roleId;
    private List<Integer> userIds;

    public List<RoleUser> convert() {
        return userIds.stream().map(userId -> new RoleUser(roleId, userId)).collect(Collectors.toList());
    }

}
