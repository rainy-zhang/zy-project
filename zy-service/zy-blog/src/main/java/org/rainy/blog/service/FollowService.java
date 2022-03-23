package org.rainy.blog.service;

import lombok.extern.slf4j.Slf4j;
import org.rainy.blog.dto.UserDto;
import org.rainy.blog.entity.Follow;
import org.rainy.blog.param.FollowParam;
import org.rainy.blog.repository.FollowRepository;
import org.rainy.common.constant.ValidateGroups;
import org.rainy.common.util.BeanValidator;
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
        // TODO: 获取请求参数中其它用户信息，调用permission服务创建用户
        UserDto userDto = null;
        Follow follow = followParam.convert();
        follow.setId(userDto.getId());
        follow.setOperator(userDto.getId());
        followRepository.save(follow);
    }

    public void unfollow() {
        // TODO: 
        //  1. 获取会话中的用户ID
        //  2. 调用permission服务根据用户ID删除用户
        Integer id = null;
        followRepository.deleteById(id);
    }

}
