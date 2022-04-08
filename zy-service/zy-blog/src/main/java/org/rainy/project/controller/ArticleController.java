package org.rainy.project.controller;

import org.rainy.project.dto.ArticleDto;
import org.rainy.project.entity.Article;
import org.rainy.project.entity.ArticleWithBlobs;
import org.rainy.project.param.ArticleParam;
import org.rainy.project.service.ArticleService;
import org.rainy.project.beans.PageResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/details/{id}")
    public ArticleWithBlobs details(@PathVariable("id") Integer id) {
        return articleService.details(id);
    }

    @PutMapping(value = "/hide/{id}")
    public void hide(@PathVariable("id") Integer id) {
        articleService.hide(id);
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
