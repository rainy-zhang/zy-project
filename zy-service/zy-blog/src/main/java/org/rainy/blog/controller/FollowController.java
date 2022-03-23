package org.rainy.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.param.FollowParam;
import org.rainy.blog.service.FollowService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping(value = "/{userId}")
    public void follow(@RequestBody FollowParam followParam) {
        followService.follow(followParam);
    }

    @DeleteMapping(value = "/")
    public void unfollow() {
        followService.unfollow();
    }

}
