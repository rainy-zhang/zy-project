package org.rainy.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.service.LikeService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/like")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping(value = "/{articleId}")
    public void like(@PathVariable("articleId") Integer articleId) {
        likeService.like(articleId);
    }

    @DeleteMapping(value = "/{id}")
    public void dislike(@PathVariable("id") Integer id) {
        likeService.dislike(id);
    }

}
