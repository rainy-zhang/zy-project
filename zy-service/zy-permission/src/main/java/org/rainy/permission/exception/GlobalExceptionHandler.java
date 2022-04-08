package org.rainy.permission.exception;

import org.rainy.common.beans.CommonResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public CommonResponse<Object> defaultErrorHandler(Exception e) {
        return CommonResponse.error(e.getMessage());
    }

}
