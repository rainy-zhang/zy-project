package org.rainy.common.constant;

/**
 * <p>
 *
 * </p>
 *
 * @author wt1734
 * @date 2021/11/26 0026 10:32
 */
public class ValidateGroups {

    public interface INSERT {};
    public interface UPDATE {};
    public interface SELECT {};
    public interface DELETE {};

    public static final Class<?> INSERT = INSERT.class;
    public static final Class<?> UPDATE = UPDATE.class;
    public static final Class<?> SELECT = SELECT.class;
    public static final Class<?> DELETE = DELETE.class;

}
