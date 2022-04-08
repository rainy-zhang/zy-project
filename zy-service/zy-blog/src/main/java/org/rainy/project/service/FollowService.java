package org.rainy.project.service;

import lombok.extern.slf4j.Slf4j;
import org.rainy.project.entity.Follow;
import org.rainy.project.param.FollowParam;
import org.rainy.project.repository.FollowRepository;
import org.rainy.project.constant.ValidateGroups;
import org.rainy.project.util.BeanValidator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FollowService {

    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public void follow(FollowParam followParam) {
        BeanValidator.validate(followParam, ValidateGroups.INSERT.class);
        Follow follow = followParam.convert();
        followRepository.save(follow);
    }

    public void unfollow() {
        // TODO: 
        //  1. 获取会话中的用户ID
        Integer id = null;
        followRepository.deleteById(id);
    }

    public Long count() {
        return followRepository.count();
    }
}
