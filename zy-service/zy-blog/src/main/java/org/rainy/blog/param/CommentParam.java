package org.rainy.blog.param;

import org.rainy.blog.entity.Comment;
import org.springframework.beans.BeanUtils;

public class CommentParam {

    public Comment convert() {
        Comment comment = new Comment();
        BeanUtils.copyProperties(this, comment);
        return comment;
    }

}
