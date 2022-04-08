package org.rainy.project.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.rainy.project.entity.Follow;
import org.rainy.project.constant.ValidateGroups;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class FollowParam {

    @NotNull(message = "邮箱不能为空", groups = {ValidateGroups.INSERT.class})
    @Length(min = 6, max = 50, message = "邮箱长度需要在6-50个字符之间", groups = {ValidateGroups.INSERT.class})
    @Email(message = "邮箱格式错误", groups = {ValidateGroups.INSERT.class})
    private String email;

    public Follow convert() {
        Follow follow = new Follow();
        BeanUtils.copyProperties(this, follow);
        return follow;
    }

}
