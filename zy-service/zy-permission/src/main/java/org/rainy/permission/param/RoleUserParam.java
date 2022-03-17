package org.rainy.permission.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rainy.permission.entity.RoleUser;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: wt1734
 * @date: 2021/11/11 0011 15:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleUserParam {

    @NotNull(message = "角色Id不能为空")
    private Integer roleId;
    private List<Integer> userIds;

    public List<RoleUser> convert() {
        return userIds.stream().map(userId -> new RoleUser(roleId, userId)).collect(Collectors.toList());
    }

}
