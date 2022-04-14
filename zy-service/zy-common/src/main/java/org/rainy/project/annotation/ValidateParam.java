package org.rainy.project.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.rainy.project.util.BeanValidator;

/**
 * 当参数需要 {@link BeanValidator} 校验是否合法时使用
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface ValidateParam {
}
