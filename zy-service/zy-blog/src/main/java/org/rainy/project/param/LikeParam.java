package org.rainy.project.param;

import lombok.Data;
import org.rainy.project.entity.Like;
import org.rainy.project.constant.ValidateGroups;
import org.springframework.beans.BeanUtils;

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
