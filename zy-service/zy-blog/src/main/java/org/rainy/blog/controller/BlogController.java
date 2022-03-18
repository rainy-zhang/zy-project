package org.rainy.blog.controller;

import org.rainy.blog.entity.Blog;
import org.rainy.blog.param.BlogParam;
import org.rainy.blog.service.BlogService;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/blog")
public class BlogController {
    
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }
    
    @GetMapping(value = "/blogs")
    public PageResult<Blog> blogs(@RequestBody PageQuery pageQuery) {
        return blogService.pageResult(pageQuery);
    }
    
    @GetMapping(value = "/{id}")
    public Blog findById(@PathVariable("id") Integer id) {
        return blogService.findById(id);
    } 
    
    @PostMapping(value = "/save")
    public void save(@RequestBody BlogParam blogParam) {
        blogService.save(blogParam);
    }
    
    @PutMapping(value = "/update")
    public void update(@RequestBody BlogParam blogParam) {
        blogService.update(blogParam);
    }
    
    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        blogService.delete(id);
    }
    
}
