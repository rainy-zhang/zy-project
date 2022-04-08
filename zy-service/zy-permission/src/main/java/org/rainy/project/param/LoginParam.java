package org.rainy.project.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class LoginParam {

    @NotNull(message = "用户名不能为空")
    @Length(min = 5, max = 50, message = "用户名称长度不合法")
    private String username;

    @NotNull(message = "密码不能为空")
    @Length(min = 5, max = 50, message = "密码长度不合法")
    private String password;

    private String ret;

}
