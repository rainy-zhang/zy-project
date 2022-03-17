package org.rainy.permission.beans;


import org.rainy.permission.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: zhangyu
 * @date: in 2021/10/30 4:38 下午
 */
public class RequestHolder {

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();
    private static final ThreadLocal<User> userHolder = new ThreadLocal<>();

    public static void set(User user) {
        userHolder.set(user);
    }

    public static void set(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static User getCurrentUser() {
        return userHolder.get();
    }

    public static HttpServletRequest getRequest() {
        return requestHolder.get();
    }

    public static void removeUser() {
        userHolder.remove();
    }

    public static void removeRequest() {
        requestHolder.remove();
    }

    public static void clear() {
        removeRequest();
        removeUser();
    }

}
