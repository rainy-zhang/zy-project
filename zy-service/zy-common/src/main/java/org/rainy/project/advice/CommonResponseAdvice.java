package org.rainy.project.advice;

import lombok.SneakyThrows;
import org.rainy.project.annotation.IgnoreResponseAdvice;
import org.rainy.project.beans.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

@RestControllerAdvice
public class CommonResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        if (Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        CommonResponse<Object> response = CommonResponse.success();
        if (o instanceof CommonResponse) {
            return o;
        }
        if (null == o) {
            return response;
        }
        response.setData(o);
        return response;
    }

}
