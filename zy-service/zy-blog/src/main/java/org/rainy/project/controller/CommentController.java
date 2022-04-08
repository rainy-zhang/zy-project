package org.rainy.project.controller;

import org.rainy.project.entity.Comment;
import org.rainy.project.param.CommentParam;
import org.rainy.project.service.CommentService;
import org.rainy.project.beans.PageQuery;
import org.rainy.project.beans.PageResult;
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
