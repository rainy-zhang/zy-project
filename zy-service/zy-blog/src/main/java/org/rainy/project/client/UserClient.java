package org.rainy.project.client;

import org.rainy.project.client.dto.User;

// TODO: RPC调用permission服务获取用户信息
public interface UserClient {

    User createVisitor(User user);

    User queryById(Integer userId);

}
