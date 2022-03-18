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
    
    @GetMapping(value = "/{blogId}")
    public PageResult<Comment> comments(@RequestBody PageQuery pageQuery, @PathVariable("blogId") Integer blogId) {
        return commentService.pageResult(pageQuery, blogId);
    }
    
    @PostMapping(value = "/save")
    public void save(@RequestBody CommentParam commentParam) {
        commentService.save(commentParam);
    }
    
}
