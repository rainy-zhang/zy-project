package org.rainy.project.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.rainy.project.exception.IllegalParamException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class BeanValidator {

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public static <T> void validate(Collection<T> beans, Class<?>... groups) {
        Preconditions.checkNotNull(beans);
        for (T bean : beans) {
            validate(bean, groups);
        }
    }

    public static <T> void validate(T bean, Class<?>... groups) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> validateResult = validator.validate(bean, groups);
        if (CollectionUtils.isEmpty(validateResult)) {
            return;
        }
        Map<String, String> errors = Maps.newLinkedHashMap();
        for (ConstraintViolation<T> validation : validateResult) {
            errors.put(validation.getPropertyPath().toString(), validation.getMessage());
        }
        if (MapUtils.isNotEmpty(errors)) {
            throw new IllegalParamException(errors.toString());
        }
    }

}
