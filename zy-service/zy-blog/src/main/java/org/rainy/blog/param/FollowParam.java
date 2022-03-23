package org.rainy.blog.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.rainy.blog.entity.Follow;
import org.rainy.common.constant.ValidateGroups;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FollowParam {

    @NotBlank(message = "用户名不能为空", groups = {ValidateGroups.INSERT.class})
    @Length(min = 5, max = 20, message = "用户名长度需要在5-20个字符之间", groups = {ValidateGroups.INSERT.class})
    private String username;

    @NotBlank(message = "昵称不能为空", groups = {ValidateGroups.INSERT.class})
    @Length(min = 5, max = 20, message = "昵称长度需要在5-20个字符之间", groups = {ValidateGroups.INSERT.class})
    private String nickname;

    @NotNull(message = "邮箱不能为空", groups = {ValidateGroups.INSERT.class})
    @Length(min = 6, max = 50, message = "邮箱长度需要在6-50个字符之间", groups = {ValidateGroups.INSERT.class})
    @Email(message = "邮箱格式错误", groups = {ValidateGroups.INSERT.class})
    private String email;

    @NotNull(message = "目标用户ID不能为空", groups = {ValidateGroups.INSERT.class})
    private Integer targetUserId;
    
    public Follow convert() {
        Follow follow = new Follow();
        BeanUtils.copyProperties(this, follow);
        return follow;
    }
    
}
