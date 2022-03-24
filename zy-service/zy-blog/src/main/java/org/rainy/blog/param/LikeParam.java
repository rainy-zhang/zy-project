package org.rainy.blog.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.rainy.blog.dto.UserDto;
import org.rainy.blog.entity.Follow;
import org.rainy.blog.entity.Like;
import org.rainy.common.constant.ValidateGroups;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class LikeParam {

    @NotNull(message = "文章ID不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private Integer articleId;

    public Like convert() {
        Like like = new Like();
        BeanUtils.copyProperties(this, like);
        return like;
    }

}
