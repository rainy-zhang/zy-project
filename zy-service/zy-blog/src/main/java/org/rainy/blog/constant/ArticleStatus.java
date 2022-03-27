package org.rainy.blog.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rainy.blog.entity.Article;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: zhangyu
 * @description:
 * @date: in 2022/3/27 9:42 下午
 */
@Getter
@AllArgsConstructor
public enum ArticleStatus {

    HIDE(0, "隐藏"),
    NORMAL(1, "正常");

    private final int code;
    private final String desc;

    public static final Map<Integer, String> map;
    static {
        map = Stream.of(ArticleStatus.values()).collect(Collectors.toMap(ArticleStatus::getCode, ArticleStatus::getDesc));
    }

}
