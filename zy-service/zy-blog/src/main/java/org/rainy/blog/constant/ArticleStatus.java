package org.rainy.blog.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArticleStatus {

    DELETED(-1, "删除"),
    NORMAL(1, "正常"),
    DRAFT(1, "草稿"),
    HIDE(2, "隐藏");

    private final int code;
    private final String desc;

}
