package org.rainy.project.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.rainy.project.annotation.ValidateParam;
import org.rainy.project.util.BeanValidator;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidateParamAspect {

    @Pointcut("execution(* org.rainy.project.service..*.*Service(..))")
    private void validatePoint() {

    }

    @Around(value = "validatePoint()")
    public Object validateAround(ProceedingJoinPoint pjp) throws Throwable {
        Class<? extends ProceedingJoinPoint> clazz = pjp.getClass();

        ValidateParam validateParam = clazz.getAnnotation(ValidateParam.class);
        if (validateParam != null) {
            Object[] args = pjp.getArgs();
            BeanValidator.validate(args);
        }
        return pjp.proceed();
    }

}
