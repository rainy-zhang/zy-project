package org.rainy.blog.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.entity.Like;
import org.rainy.blog.repository.LikeRepository;
import org.rainy.common.exception.BeanNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final ArticleService articleService;

    public LikeService(LikeRepository likeRepository, ArticleService articleService) {
        this.likeRepository = likeRepository;
        this.articleService = articleService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void like(Integer articleId) {
        Preconditions.checkNotNull(articleId, "文章ID不能为空");
        Like like = new Like(articleId);
        likeRepository.save(like);
        articleService.increLike(articleId);
        log.info("点赞成功，articleId：{}, like：{}", articleId, like);
    }

    @Transactional(rollbackFor = Exception.class)
    public void dislike(Integer id) {
        Preconditions.checkNotNull(id, "ID不能为空");
        Like like = likeRepository.findById(id).orElseThrow(() -> new BeanNotFoundException("点赞记录不存在"));
        likeRepository.deleteById(id);
        articleService.subtractLike(like.getArticleId());
        log.info("取消点赞成功，articleId：{}，like：{}", like.getArticleId(), like);
    }

}
