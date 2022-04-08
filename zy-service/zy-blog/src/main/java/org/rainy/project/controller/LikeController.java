package org.rainy.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.rainy.project.param.LikeParam;
import org.rainy.project.service.LikeService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/like")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public void like(@RequestBody LikeParam likeParam) {
        likeService.like(likeParam);
    }

    @DeleteMapping(value = "/{id}")
    public void dislike(@PathVariable("id") Integer id) {
        likeService.dislike(id);
    }

}
