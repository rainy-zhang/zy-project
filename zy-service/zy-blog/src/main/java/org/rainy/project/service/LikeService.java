package org.rainy.project.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.project.entity.Like;
import org.rainy.project.param.LikeParam;
import org.rainy.project.repository.LikeRepository;
import org.rainy.project.constant.ValidateGroups;
import org.rainy.project.exception.BeanNotFoundException;
import org.rainy.project.util.BeanValidator;
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
    public void like(LikeParam likeParam) {
        BeanValidator.validate(likeParam, ValidateGroups.INSERT.class);
        Like like = likeParam.convert();
        likeRepository.save(like);
        articleService.increaseLikes(like.getArticleId());
        log.info("点赞成功，articleId：{}, like：{}", like.getArticleId(), like);
    }

    @Transactional(rollbackFor = Exception.class)
    public void dislike(Integer id) {
        Preconditions.checkNotNull(id, "ID不能为空");
        Like like = likeRepository.findById(id).orElseThrow(() -> new BeanNotFoundException("点赞记录不存在"));
        likeRepository.deleteById(id);
        articleService.subtractLikes(like.getArticleId());
        log.info("取消点赞成功，articleId：{}，like：{}", like.getArticleId(), like);
    }

}
