package org.rainy.project.service;

import lombok.extern.slf4j.Slf4j;
import org.rainy.project.dto.StatisticsDto;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StatisticsService {

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final FollowService followService;

    public StatisticsService(ArticleService articleService, CategoryService categoryService, TagService tagService, FollowService followService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.followService = followService;
    }

    public StatisticsDto statistics() {
        return StatisticsDto.builder()
                .articleCount(articleService.count())
                .followCount(followService.count())
                .tagCount(tagService.count())
                .categoryCount(categoryService.count())
                .build();
    }

}
