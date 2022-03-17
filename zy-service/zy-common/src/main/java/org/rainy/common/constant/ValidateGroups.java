package org.rainy.common.constant;

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
