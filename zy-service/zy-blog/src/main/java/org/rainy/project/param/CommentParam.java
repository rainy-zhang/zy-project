package org.rainy.project.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.rainy.project.entity.Comment;
import org.rainy.project.constant.ValidateGroups;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentParam {

    @NotBlank(message = "评论内容不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    @Length(min = 1, max = 200, message = "评论内容长度在1-200个字符之间", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private String content;

    private Integer replyId;

    @NotNull(message = "文章ID不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private Integer articleId;

    private Integer status;

    public Comment convert() {
        Comment comment = new Comment();
        BeanUtils.copyProperties(this, comment);
        return comment;
    }

}
