package org.rainy.blog.controller;

import org.rainy.blog.dto.ArchiveDto;
import org.rainy.blog.dto.ArticleDto;
import org.rainy.blog.entity.Article;
import org.rainy.blog.param.ArticleParam;
import org.rainy.blog.service.ArticleService;
import org.rainy.common.beans.PageResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping(value = "/articles")
    public PageResult<ArticleDto> articlePage(@RequestBody ArticleParam articleParam) {
        return articleService.articlePage(articleParam);
    }

    @GetMapping(value = "/{id}")
    public Article findById(@PathVariable("id") Integer id) {
        return articleService.findById(id);
    }

    @GetMapping(value = "/archives")
    public List<ArchiveDto> archives() {
        return Collections.emptyList();
    }

    @GetMapping(value = "/tops")
    public List<Article> tops() {
        return Collections.emptyList();
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
