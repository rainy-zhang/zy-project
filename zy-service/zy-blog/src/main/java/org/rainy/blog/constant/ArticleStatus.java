package org.rainy.blog.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArticleStatus {
    
    DELETED(-1, "删除"),
    HIDE(0, "隐藏"),
    NORMAL(1,"正常"),
    DRAFT(2, "草稿");
    
    private final int code;
    private final String desc;
    
}
