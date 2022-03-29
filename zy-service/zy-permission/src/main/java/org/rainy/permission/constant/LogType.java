package org.rainy.permission.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum LogType {

    USER(1, "用户"),
    ROLE(2, "角色"),
    ACL(3, "权限点"),
    ACL_MODULE(4, "权限模块"),
    ROLE_USER(5, "角色与用户关联关系"),
    ROLE_ACL(6, "角色与权限关联关系"),
    ;

    private final int code;
    private final String desc;

    public static final Map<Integer, LogType> types;

    static {
        types = Stream.of(LogType.values()).collect(Collectors.toMap(LogType::getCode, Function.identity()));
    }


}
