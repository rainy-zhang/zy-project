package org.rainy.permission.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.permission.entity.AclModule;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: wt1734
 * @date: 2021/11/9 0009 16:32
 */
@Data
public class AclModuleParam {

    @NotNull(message = "权限模块ID不能为空", groups = {ValidateGroups.UPDATE.class})
    private Integer id;

    @NotNull(message = "权限模块名称不能为空", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Length(min = 1, max = 10, message = "权限模块名称长度需要在10个字符以内", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private String name;

    private Integer parentId;

    @NotNull(message = "权限模块顺序号不能为空", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Min(value = -1, message = "权限模块顺序号不合法", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private Integer seq;

    @NotNull(message = "权限模块状态不能为空", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Min(value = 0, message = "权限模块状态不合法", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Max(value = 1, message = "权限模块状态不合法", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private Integer status;

    @Length(max = 200, message = "备注长度需要在200个字符以内", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private String remark;

    public AclModule convert() {
        AclModule aclModule = new AclModule();
        BeanUtils.copyProperties(this, aclModule);
        return aclModule;
    }


}
