package org.rainy.permission.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.util.PasswordUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class VisitorParam {

    @NotNull(message = "昵称不能为空", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Length(min = 5, max = 20, message = "昵称长度需要在5-20个字符之间", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private String nickname;

    @NotNull(message = "邮箱不能为空", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Length(min = 6, max = 50, message = "邮箱长度需要在6-50个字符之间", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    @Email(message = "邮箱格式错误", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private String email;

    @Length(max = 100, message = "个人网站地址长度需要在100个字符以内", groups = {ValidateGroups.UPDATE.class, ValidateGroups.INSERT.class})
    private String website;

    public UserParam convert() {
        return UserParam.builder()
                .email(this.email)
                .password(visitorPassword())
                .username(visitorUsername())
                .nickname(this.nickname)
                .build();

    }

    private String visitorPassword() {
        return PasswordUtils.encrypt("123456");
    }

    private String visitorUsername() {
        return this.email.split("@")[0];
    }
}
