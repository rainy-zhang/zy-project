package org.rainy.blog.client;

import org.rainy.blog.dto.UserDto;

// TODO: RPC调用permission服务获取用户信息
public interface UserClient {
    
    UserDto createUser();
    
    UserDto queryById(Integer userId);
    
}
