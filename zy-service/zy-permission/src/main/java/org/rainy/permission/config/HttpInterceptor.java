package org.rainy.permission.config;

import lombok.extern.slf4j.Slf4j;
import org.rainy.common.util.JsonMapper;
import org.rainy.permission.beans.RequestHolder;
import org.rainy.permission.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class HttpInterceptor implements HandlerInterceptor {

    public static final String START_TIME = "REQUEST_START_TIME";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        Map<String, String[]> parameters = request.getParameterMap();
        log.info("request start uri: {}, params: {}", uri, JsonMapper.object2String(parameters));
        request.setAttribute(START_TIME, System.currentTimeMillis());

        RequestHolder.set(new User());
        RequestHolder.set(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String uri = request.getRequestURI();
        long start = (long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        log.info("request complete uri: {}, cost: {}ms", uri, end - start);
        RequestHolder.clear();
    }
}
