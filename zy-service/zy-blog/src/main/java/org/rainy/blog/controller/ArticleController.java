package org.rainy.blog.controller;

import org.rainy.blog.constant.ArticleStatus;
import org.rainy.blog.dto.ArticleDto;
import org.rainy.blog.entity.Article;
import org.rainy.blog.entity.ArticleWithBlobs;
import org.rainy.blog.param.ArticleParam;
import org.rainy.blog.service.ArticleService;
import org.rainy.common.beans.PageResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping(value = "/articles/all")
    public PageResult<ArticleDto> allArticlePage(@RequestBody ArticleParam articleParam) {
        return articleService.articlePage(articleParam);
    }

    @PostMapping(value = "/articles")
    public PageResult<ArticleDto> articlePage(@RequestBody ArticleParam articleParam) {
        articleParam.setStatus(ArticleStatus.NORMAL.getCode());
        return articleService.articlePage(articleParam);
    }

    @GetMapping(value = "/details/{id}")
    public ArticleWithBlobs details(@PathVariable("id") Integer id) {
        return articleService.details(id);
    }

    @GetMapping(value = "/hots")
    public List<Article> hots() {
        return articleService.hots();
    }

    @PostMapping(value = "/save")
    public void save(@RequestBody ArticleParam articleParam) {
        articleService.save(articleParam);
    }

    @PutMapping(value = "/update")
    public void update(@RequestBody ArticleParam articleParam) {
        articleService.update(articleParam);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        articleService.delete(id);
    }

}
