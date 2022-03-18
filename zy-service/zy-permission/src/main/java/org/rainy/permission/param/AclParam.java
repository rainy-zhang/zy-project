package org.rainy.permission.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.permission.entity.Acl;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AclParam {

    @NotNull(message = "权限Id不能为空", groups = {ValidateGroups.UPDATE.class})
    private Integer id;

    @NotNull(message = "权限名称不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    @Length(min = 1, max = 10, message = "权限名称长度需要在10个字符以内", groups = {ValidateGroups.class, ValidateGroups.UPDATE.class})
    private String name;

    @NotNull(message = "所属权限模块不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private Integer aclModuleId;

    @NotNull(message = "权限请求路径不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    @Length(min = 1, max = 100, message = "权限请求路径长度需要在100字符以内", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private String url;

    @NotNull(message = "权限状态不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    @Min(value = 0, message = "权限状态不合法", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    @Max(value = 1, message = "权限状态不合法", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private Integer status;

    @NotNull(message = "权限顺序号不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    @Min(value = -1, message = "权限顺序号不合法", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private Integer seq;

    @Length(max = 200,message = "备注需要在200个字符以内", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private String remark;

    public Acl convert() {
        Acl acl = new Acl();
        BeanUtils.copyProperties(this, acl);
        return acl;
    }

}
