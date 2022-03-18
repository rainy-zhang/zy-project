package org.rainy.permission.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.permission.entity.Role;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RoleParam {

    @NotNull(message = "角色id不能为空", groups = {ValidateGroups.UPDATE.class})
    private Integer id;

    @NotNull(message = "角色名称不能为空", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Length(min = 1, max = 10, message = "角色名称长度需要在10个字符以内", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private String name;

    @NotNull(message = "角色状态不能为空", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Min(value = 0, message = "角色状态不合法", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Max(value = 1, message = "角色状态不合法", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private Integer status;

    @Length(max = 200, message = "备注长度需要在200个字符以内", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private String remark;

    public Role convert() {
        Role role = new Role();
        BeanUtils.copyProperties(this, role);
        return role;
    }


}
