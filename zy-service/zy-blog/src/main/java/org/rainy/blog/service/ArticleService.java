package org.rainy.blog.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.constant.ArticleStatus;
import org.rainy.blog.dto.ArticleDto;
import org.rainy.blog.entity.Article;
import org.rainy.blog.entity.ArticleWithBlobs;
import org.rainy.blog.param.ArticleParam;
import org.rainy.blog.repository.ArticleRepository;
import org.rainy.blog.repository.ArticleWithBlogRepository;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.util.BeanValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
public class ArticleService {

    public static Set<Integer> calculateArticleIds = Sets.newHashSet();
    private final ArticleRepository articleRepository;
    private final ArticleWithBlogRepository articleWithBlogRepository;
    private final ArticleTagService articleTagService;
    private final CategoryService categoryService;
    private final TagService tagService;

    public ArticleService(ArticleRepository articleRepository, ArticleWithBlogRepository articleWithBlogRepository, ArticleTagService articleTagService, CategoryService categoryService, TagService tagService) {
        this.articleRepository = articleRepository;
        this.articleWithBlogRepository = articleWithBlogRepository;
        this.articleTagService = articleTagService;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    public PageResult<ArticleDto> articlePage(ArticleParam articleParam) {
        BeanValidator.validate(articleParam, ValidateGroups.SELECT.class);
        Specification<Article> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (Objects.nonNull(articleParam.getStatus())) {
                Predicate predicate = criteriaBuilder.equal(root.get(Article.COLUMN.STATUS), ArticleStatus.NORMAL.getCode());
                predicates.add(predicate);
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        PageQuery articlePageQuery = articleParam.getArticlePageQuery();
        Page<Article> page = articleRepository.findAll(specification, articlePageQuery.convert());
        Page<ArticleDto> articlePage = page.map(article -> {
            Integer articleId = article.getId();
            List<Integer> tagIds = articleTagService.findTagIdsByArticleId(articleId);
            // 获取文章对应的评论列表
            return new ArticleDto.Builder()
                    .id(articleId)
                    .comments(article.getComments())
                    .likes(article.getLikes())
                    .reading(article.getReading())
                    .title(article.getTitle())
                    .summary(article.getSummary())
                    .userId(article.getUserId())
                    .createTime(article.getCreateTime())
                    .category(categoryService.findById(article.getCategoryId()))
                    .tags(tagService.findByIds(tagIds))
                    .status(article.getStatus())
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
     * 根据id查询文章内容
     *
     * @param id 文章ID
     * @return {@link ArticleWithBlobs}
     */
    public ArticleWithBlobs details(Integer id) {
        Preconditions.checkNotNull(id, "文章id不能为空");
        ArticleWithBlobs article = articleWithBlogRepository.findById(id).orElseThrow(() -> new BeanNotFoundException("文章不存在"));
        increaseReads(id);
        return article;
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

    /**
     * 热度最高的5篇文章
     *
     * @return {@link List<Article>}
     */
    public List<Article> hots() {
        Specification<Article> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Article.COLUMN.STATUS), ArticleStatus.NORMAL.getCode());
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Order.desc(Article.COLUMN.HEAD)));
        return articleRepository.findAll(specification, pageRequest).getContent();
    }

    public long countByCategoryId(Integer categoryId) {
        Preconditions.checkNotNull(categoryId, "分类id不能为空");
        Specification<Article> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Article.COLUMN.CATEGORY_ID), categoryId);
        return articleRepository.count(specification);
    }

    /**
     * 减少文章点赞数量
     *
     * @param id 文章ID
     */
    public void subtractLikes(Integer id) {
        Article article = findById(id);
        article.setLikes(article.getLikes() - 1);
        articleRepository.save(article);
    }

    /**
     * 增加文章点赞数量
     *
     * @param id 文章ID
     */
    public void increaseLikes(Integer id) {
        Article article = findById(id);
        article.setLikes(article.getLikes() + 1);
        articleRepository.save(article);
    }

    /**
     * 增加文章阅读次数
     *
     * @param id 文章ID
     */
    public void increaseReads(Integer id) {
        Article article = findById(id);
        article.setReading(article.getReading() + 1);
        articleRepository.save(article);
    }

    /**
     * 增加评论数
     *
     * @param id 文章ID
     */
    public void increaseComments(Integer id) {
        Article article = findById(id);
        article.setComments(article.getComments() + 1);
        articleRepository.save(article);
    }

    public Long count() {
        Specification<Article> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Article.COLUMN.STATUS), ArticleStatus.NORMAL.getCode());
        return articleRepository.count(specification);
    }

    // TODO: 定时任务
    public void calculateScore() {
        List<Article> articles = articleRepository.findAllById(calculateArticleIds);
        articles.forEach(article -> {
            CalculateHopArticle calculateHopArticle = new CalculateHopArticle(article);
            BigDecimal heat = calculateHopArticle.calculateHeat();
            article.setHeat(heat);
        });
        articleRepository.saveAll(articles);
        calculateArticleIds.clear();
    }

    private static class CalculateHopArticle {

        public static final BigDecimal COMMENT_WEIGHTS = new BigDecimal("0.5");
        public static final BigDecimal LIKES_WEIGHTS = new BigDecimal("0.3");
        public static final BigDecimal READS_WEIGHTS = new BigDecimal("0.2");

        private final BigDecimal comments;
        private final BigDecimal likes;
        private final BigDecimal reads;

        public CalculateHopArticle(Article article) {
            this.comments = new BigDecimal(article.getComments());
            this.likes = new BigDecimal(article.getLikes());
            this.reads = new BigDecimal(article.getReading());
        }

        /**
         * 热度计算规则：(评论数 * 0.5) + (点赞数 * 0.3) + (阅读量 * 0.2)
         *
         * @return 热度分值
         */
        public BigDecimal calculateHeat() {
            return COMMENT_WEIGHTS.multiply(this.comments).add(LIKES_WEIGHTS.multiply(this.likes)).add(READS_WEIGHTS.multiply(this.reads));
        }

    }


}
