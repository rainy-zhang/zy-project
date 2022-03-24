package org.rainy.blog.util;

import org.rainy.blog.dto.ArticleDto;

import java.math.BigDecimal;

public class HotArticleCalculator {

    public static BigDecimal calculateScore(ArticleDto articleDto) {
        Long commentCount = articleDto.getCommentCount();
        Long likes = articleDto.getLikes();
        Long reads = articleDto.getReads();
        
        return BigDecimal.ZERO;
    }


}
