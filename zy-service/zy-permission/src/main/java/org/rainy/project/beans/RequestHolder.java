package org.rainy.project.beans;

import org.rainy.project.entity.User;

import javax.servlet.http.HttpServletRequest;

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
