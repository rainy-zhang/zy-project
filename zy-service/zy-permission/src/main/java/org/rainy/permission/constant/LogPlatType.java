package org.rainy.permission.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 *
 * </p>
 *
 * @author wt1734
 * @date 2021/12/2 0002 11:19
 */
@Getter
@AllArgsConstructor
public enum LogPlatType {

    PERMISSION("PERMISSION"),
    TRAVEL("TRAVEL"),
    DIARY("DIARY"),
    NOTE( "NOTE"),
    ;

    private final String value;

}
