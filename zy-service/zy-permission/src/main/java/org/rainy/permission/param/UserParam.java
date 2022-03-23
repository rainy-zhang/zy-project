package org.rainy.permission.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.permission.entity.User;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserParam {

    @NotNull(message = "用户id不能为空", groups = {ValidateGroups.UPDATE.class})
    private Integer id;

    @NotNull(message = "用户名不能为空", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Length(min = 5, max = 20, message = "用户名长度需要在5-20个字符之间", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private String username;

    @NotNull(message = "昵称不能为空", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Length(min = 5, max = 20, message = "昵称长度需要在5-20个字符之间", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private String nickname;

    @NotNull(message = "邮箱不能为空", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Length(min = 6, max = 50, message = "邮箱长度需要在6-50个字符之间", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Email(message = "邮箱格式错误", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private String email;

    @NotNull(message = "密码不能为空", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Length(min = 5, max = 20, message = "密码长度需要在5-20个字符之间", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private String password;

    @Length(max = 200, message = "备注长度需要在200个字符以内", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private String remark;

    @NotNull(message = "用户状态不能为空", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Min(value = 0, message = "用户状态不合法", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Max(value = 1, message = "用户状态不合法", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private Integer status;

    @NotNull(message = "排序号不能为空", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Min(value = -1, message = "排序号不合法", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private Integer seq;

    public User convert() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }



}
