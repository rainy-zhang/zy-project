package org.rainy.project.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.rainy.project.entity.ArticleTag;
import org.rainy.project.repository.ArticleTagRepository;
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

    public void changeArticleTag(List<Integer> tagIds, Integer articleId) {
        articleTagRepository.deleteByArticleId(articleId);
        Preconditions.checkNotNull(tagIds, "标签ID列表不能为空");
        Preconditions.checkNotNull(articleId, "文章ID不能为空");

        List<Integer> originTagIds = articleTagRepository.findTagIdsByArticleId(articleId);
        if (CollectionUtils.isEqualCollection(originTagIds, tagIds)) {
            return;
        }
        List<ArticleTag> articleTags = tagIds.stream().map(tagId -> ArticleTag.builder().tagId(tagId).articleId(articleId).build()).collect(Collectors.toList());
        articleTagRepository.saveAll(articleTags);
    }

    public List<Integer> findTagIdsByArticleId(Integer articleId) {
        Preconditions.checkNotNull(articleId, "文章ID不能为空");
        return articleTagRepository.findTagIdsByArticleId(articleId);
    }

}
