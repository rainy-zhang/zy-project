package org.rainy.blog.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.rainy.blog.constant.ArticleStatus;
import org.rainy.blog.dto.ArticleDto;
import org.rainy.blog.entity.Article;
import org.rainy.blog.entity.Comment;
import org.rainy.blog.param.ArticleParam;
import org.rainy.blog.repository.ArticleRepository;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.util.BeanValidator;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleTagService articleTagService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final CommentService commentService;

    public ArticleService(ArticleRepository articleRepository, ArticleTagService articleTagService, CategoryService categoryService, TagService tagService, CommentService commentService) {
        this.articleRepository = articleRepository;
        this.articleTagService = articleTagService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.commentService = commentService;
    }

    public PageResult<ArticleDto> articlePage(ArticleParam articleParam) {
        BeanValidator.validate(articleParam, ValidateGroups.SELECT.class);
        Specification<Article> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Article.COLUMN.STATUS), ArticleStatus.NORMAL.getCode());
        
        PageQuery articlePageQuery = articleParam.getArticlePageQuery();
        PageQuery commentPageQuery = articleParam.getCommentPageQuery();
        Page<Article> page = articleRepository.findAll(specification, articlePageQuery.convert());
        Page<ArticleDto> articlePage = page.map(article -> {
            Integer articleId = article.getId();
            List<Integer> tagIds = articleTagService.findTagIdsByArticleId(articleId);
            // 获取文章对应的评论列表
            PageResult<Comment> commentPageResult = commentService.pageResult(commentPageQuery, articleId);
            return new ArticleDto.Builder()
                    .article(article)
                    .category(categoryService.findByArticleId(articleId))
                    .tags(tagService.findByIds(tagIds))
                    .comments(commentPageResult.getData())
                    .commentCount(commentService.countByArticleId(articleId))
                    .build();
        });
        return PageResult.of(articlePage);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(ArticleParam articleParam) {
        BeanValidator.validate(articleParam, ValidateGroups.INSERT.class);
        Article article = articleRepository.save(articleParam.convert());

        Integer articleId = article.getId();
        List<Integer> tagIds = articleParam.getTagIds();
        articleTagService.changeArticleTag(tagIds, articleId);
        log.info("保存文章成功，article：{}", article);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(ArticleParam articleParam) {
        BeanValidator.validate(articleParam, ValidateGroups.UPDATE.class);
        Integer articleId = articleParam.getId();
        Article originArticle = findById(articleId);
        List<Integer> originTagIds = articleTagService.findTagIdsByArticleId(articleId);
        List<Integer> tagIds = articleParam.getTagIds();
        if (!CollectionUtils.isEqualCollection(originTagIds, tagIds)) {
            articleTagService.changeArticleTag(tagIds, articleId);
        }
        Article article = articleParam.convert();
        if (Objects.equals(article, originArticle)) {
            articleRepository.save(article);
        }
    }

    public Article findById(Integer id) {
        Preconditions.checkNotNull(id, "文章id不能为空");
        return articleRepository.findById(id).orElseThrow(() -> new BeanNotFoundException("文章不存在"));
    }

    public void delete(Integer id) {
        Preconditions.checkNotNull(id);
        articleRepository.deleteById(id);
        log.info("删除文章成功，articleId：{}", id);
    }

    /**
     * 减少文章点赞数量
     * @param id
     */
    public void subtractLike(Integer id) {
        Article article = findById(id);
        article.setLikes(article.getLikes()-1);
        articleRepository.save(article);
    }

    /**
     * 增加文章点赞数量
     * @param id
     */
    public void increLike(Integer id) {
        Article article = findById(id);
        article.setLikes(article.getLikes()+1);
        articleRepository.save(article);
    }

    /**
     * 增加文章阅读次数
     * @param id
     */
    public void increReading(Integer id) {
        Article article = findById(id);
        article.setReading(article.getReading());
        articleRepository.save(article);
    }

    

}
