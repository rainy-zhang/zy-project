package org.rainy.blog.client;

import org.rainy.blog.client.dto.User;

// TODO: RPC调用permission服务获取用户信息
public interface UserClient {

    User createVisitor(User user);

    User queryById(Integer userId);

}
