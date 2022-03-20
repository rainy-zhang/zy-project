package org.rainy.blog.controller;

import org.rainy.blog.entity.Comment;
import org.rainy.blog.param.CommentParam;
import org.rainy.blog.service.CommentService;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    @GetMapping(value = "/{articleId}")
    public PageResult<Comment> comments(@RequestBody PageQuery pageQuery, @PathVariable("articleId") Integer articleId) {
        return commentService.pageResult(pageQuery, articleId);
    }
    
    @PostMapping(value = "/save")
    public void save(@RequestBody CommentParam commentParam) {
        commentService.save(commentParam);
    }
    
}
