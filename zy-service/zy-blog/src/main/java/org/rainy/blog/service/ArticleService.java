package org.rainy.blog.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.dto.ArticleDto;
import org.rainy.blog.entity.Article;
import org.rainy.blog.entity.ArticleWithBlobs;
import org.rainy.blog.entity.Comment;
import org.rainy.blog.param.ArticleParam;
import org.rainy.blog.repository.ArticleRepository;
import org.rainy.blog.repository.ArticleWithBlogRepository;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.common.constant.CommonStatus;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.util.BeanValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleWithBlogRepository articleWithBlogRepository;
    private final ArticleTagService articleTagService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final CommentService commentService;

    public ArticleService(ArticleRepository articleRepository, ArticleWithBlogRepository articleWithBlogRepository, ArticleTagService articleTagService, CategoryService categoryService, TagService tagService, CommentService commentService) {
        this.articleRepository = articleRepository;
        this.articleWithBlogRepository = articleWithBlogRepository;
        this.articleTagService = articleTagService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.commentService = commentService;
    }

    public PageResult<ArticleDto> articlePage(ArticleParam articleParam) {
        BeanValidator.validate(articleParam, ValidateGroups.SELECT.class);
        Specification<Article> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Article.COLUMN.STATUS), CommonStatus.VALID.getCode());

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
                    .category(categoryService.findById(article.getCategoryId()))
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
        ArticleWithBlobs article = articleWithBlogRepository.save(articleParam.convert());

        Integer articleId = article.getId();
        List<Integer> tagIds = articleParam.getTagIds();
        articleTagService.changeArticleTag(tagIds, articleId);
        log.info("保存文章成功，article：{}", article);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(ArticleParam articleParam) {
        BeanValidator.validate(articleParam, ValidateGroups.UPDATE.class);
        ArticleWithBlobs article = articleParam.convert();
        Integer articleId = article.getId();
        articleTagService.changeArticleTag(articleParam.getTagIds(), articleId);
        articleWithBlogRepository.save(article);
    }

    /**
     * 
     * @param id
     * @return
     */
    public ArticleWithBlobs findBlobsById(Integer id) {
        Preconditions.checkNotNull(id, "文章id不能为空");
        return articleWithBlogRepository.findById(id).orElseThrow(() -> new BeanNotFoundException("文章不存在"));
    }

    public Article findById(Integer id) {
        Preconditions.checkNotNull(id, "文章id不能为空");
        return articleRepository.findById(id).orElseThrow(() -> new BeanNotFoundException("文章不存在"));
    }

    public void delete(Integer id) {
        Preconditions.checkNotNull(id, "文章id不能为空");
        articleRepository.deleteById(id);
        log.info("删除文章成功，articleId：{}", id);
    }

    public long countByCategoryId(Integer categoryId) {
        Preconditions.checkNotNull(categoryId, "分类id不能为空");
        Specification<Article> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Article.COLUMN.CATEGORY_ID), categoryId);
        return articleRepository.count(specification);
    }

    /**
     * 减少文章点赞数量
     *
     * @param id
     */
    public void subtractLike(Integer id) {
        Article article = findById(id);
        article.setLikes(article.getLikes() - 1);
        articleRepository.save(article);
    }

    /**
     * 增加文章点赞数量
     *
     * @param id
     */
    public void increaseLike(Integer id) {
        Article article = findById(id);
        article.setLikes(article.getLikes() + 1);
        articleRepository.save(article);
    }

    /**
     * 增加文章阅读次数
     *
     * @param id
     */
    public void increaseReading(Integer id) {
        Article article = findById(id);
        article.setReads(article.getReads() + 1);
        articleRepository.save(article);
    }

    public Long count() {
        Specification<Article> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Article.COLUMN.STATUS), CommonStatus.VALID.getCode());
        return articleRepository.count(specification);
    }
}
