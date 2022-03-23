package org.rainy.blog.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.dto.ArticleDto;
import org.rainy.blog.entity.Article;
import org.rainy.blog.param.ArticleParam;
import org.rainy.blog.repository.ArticleRepository;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.util.BeanValidator;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleTagService articleTagService;
    private final CategoryService categoryService;
    private final TagService tagService;

    public ArticleService(ArticleRepository articleRepository, ArticleTagService articleTagService, CategoryService categoryService, TagService tagService) {
        this.articleRepository = articleRepository;
        this.articleTagService = articleTagService;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }


    public PageResult<ArticleDto> pageResult(PageQuery pageQuery) {
        BeanValidator.validate(pageQuery, ValidateGroups.SELECT.class);
        Page<Article> page = articleRepository.findAll(pageQuery.convert());
        Page<ArticleDto> articlePage = page.map(article -> {
            List<Integer> tagIds = articleTagService.findTagIdsByArticleId(article.getId());
            return new ArticleDto.Builder()
                    .article(article)
                    .author("")
                    .category(categoryService.findByArticleId(article.getId()))
                    .tags(tagService.findByIds(tagIds))
                    .build();
        });
        return PageResult.of(articlePage);
    }

    public Article findById(Integer id) {
        Preconditions.checkNotNull(id, "文章id不能为空");
        return articleRepository.findById(id).orElseThrow(() -> new BeanNotFoundException("文章不存在"));
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(ArticleParam articleParam) {
        BeanValidator.validate(articleParam, ValidateGroups.INSERT.class);
        Article article = articleRepository.save(articleParam.convert());

        Integer articleId = article.getId();
        List<Integer> tagIds = articleParam.getTagIds();
        articleTagService.save(tagIds, articleId);
        log.info("保存文章成功：{}", article);
    }

    public void delete(Integer id) {
        Preconditions.checkNotNull(id);
        articleRepository.deleteById(id);
        log.info("删除文章成功：{}", id);
    }

    public void update(ArticleParam articleParam) {
        BeanValidator.validate(articleParam, ValidateGroups.UPDATE.class);
        Article article = articleParam.convert();
        articleRepository.save(article);
    }

}
