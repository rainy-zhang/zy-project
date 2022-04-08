package org.rainy.project.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {

    public static final int SUCCESS_CODE = 1;
    public static final int ERROR_CODE = -1;
    public static final String DEFAULT_SUCCESS_MESSAGE = "success";
    public static final String DEFAULT_ERROR_MESSAGE = "error";

    private int code;
    private String message;
    private T data;

    public static <T> CommonResponse<T> success() {
        return CommonResponse.<T>builder().code(SUCCESS_CODE).message(DEFAULT_SUCCESS_MESSAGE).build();
    }

    public static <T> CommonResponse<T> success(T data) {
        return CommonResponse.<T>builder().code(SUCCESS_CODE).message(DEFAULT_SUCCESS_MESSAGE).data(data).build();
    }

    public static <T> CommonResponse<T> error(String message) {
        return CommonResponse.<T>builder().code(ERROR_CODE).message(message).build();
    }

    public static <T> CommonResponse<T> error() {
        return CommonResponse.<T>builder().code(ERROR_CODE).message(DEFAULT_ERROR_MESSAGE).build();
    }

}
