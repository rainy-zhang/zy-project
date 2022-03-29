package org.rainy.permission.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum LogOpType {

    INSERT(1, "新增"),
    DELETE(2, "删除"),
    UPDATE(3, "修改");

    private final int code;
    private final String desc;

    public static final Map<Integer, LogOpType> opTypes;

    static {
        opTypes = Stream.of(LogOpType.values()).collect(Collectors.toMap(LogOpType::getCode, Function.identity()));
    }

}
