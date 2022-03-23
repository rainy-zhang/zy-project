package org.rainy.blog.controller;

import org.rainy.blog.dto.ArticleDto;
import org.rainy.blog.entity.Article;
import org.rainy.blog.param.ArticleParam;
import org.rainy.blog.service.ArticleService;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/article")
public class ArticleController {
    
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }
    
    @GetMapping(value = "/articles")
    public PageResult<ArticleDto> articles(@RequestBody PageQuery pageQuery) {
        return articleService.pageResult(pageQuery);
    }
    
    @GetMapping(value = "/{id}")
    public Article findById(@PathVariable("id") Integer id) {
        return articleService.findById(id);
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
