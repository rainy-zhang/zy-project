package org.rainy.blog.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.entity.ArticleTag;
import org.rainy.blog.repository.ArticleTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleTagService {
    
    private final ArticleTagRepository articleTagRepository;

    public ArticleTagService(ArticleTagRepository articleTagRepository) {
        this.articleTagRepository = articleTagRepository;
    }
    
    public void save(List<Integer> tagIds, Integer articleId) {
        Preconditions.checkNotNull(tagIds, "标签ID列表不能为空");
        Preconditions.checkNotNull(articleId, "文章ID不能为空");
        List<ArticleTag> articleTags = tagIds.stream().map(tagId -> ArticleTag.builder().tagId(tagId).articleId(articleId).build()).collect(Collectors.toList());
        articleTagRepository.saveAll(articleTags);
    }
    
    public List<Integer> findTagIdsByArticleId(Integer articleId) {
        Preconditions.checkNotNull(articleId, "文章ID不能为空");
        return articleTagRepository.findTagIdsByArticleId(articleId);
    }
    
}
