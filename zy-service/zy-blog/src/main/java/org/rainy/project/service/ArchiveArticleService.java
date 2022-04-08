package org.rainy.project.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.rainy.project.entity.ArchiveArticle;
import org.rainy.project.repository.ArchiveArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArchiveArticleService {

    private final ArchiveArticleRepository archiveArticleRepository;

    public ArchiveArticleService(ArchiveArticleRepository archiveArticleRepository) {
        this.archiveArticleRepository = archiveArticleRepository;
    }

    /**
     * 根据归档ID获取对应的文章Id列表
     *
     * @param archiveId
     * @return
     */
    public List<Integer> findArticleIdsByArchiveId(Integer archiveId) {
        Preconditions.checkNotNull(archiveId, "归档ID不能为空");
        return archiveArticleRepository.findArticleIdsByArchiveId(archiveId);
    }

    /**
     * 更新文章与归档关联关系
     *
     * @param archiveId
     * @param articleIds
     */
    public void changeArchiveArticle(Integer archiveId, List<Integer> articleIds) {
        Preconditions.checkNotNull(archiveId, "归档ID不能为空");
        Preconditions.checkNotNull(articleIds, "文章ID列表不能为空");

        List<Integer> originArticleIds = archiveArticleRepository.findArticleIdsByArchiveId(archiveId);
        if (CollectionUtils.isEqualCollection(originArticleIds, articleIds)) {
            return;
        }

        List<ArchiveArticle> archiveArticles = articleIds.stream().map(articleId -> ArchiveArticle.builder().articleId(articleId).archiveId(archiveId).build()).collect(Collectors.toList());
        archiveArticleRepository.saveAll(archiveArticles);
        log.info("更新文章与归档关联关系成功，归档ID：{}，文章ID列表：{}", archiveId, articleIds);
    }

}
