package org.rainy.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum CommonStatus {

    INVALID(0, "无效"),
    VALID(1, "有效");

    private final int code;
    private final String desc;

    public static final Map<Integer, CommonStatus> statusMap;
    static {
        statusMap = Stream.of(CommonStatus.values()).collect(Collectors.toMap(CommonStatus::getCode, Function.identity()));
    }

}
