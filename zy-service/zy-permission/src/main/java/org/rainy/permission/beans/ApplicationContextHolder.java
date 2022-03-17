package org.rainy.permission.beans;

import com.google.common.base.Preconditions;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: wt1734
 * @date: 2021/11/11 0011 10:21
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }

    public static <T> T popBean(String beanName, Class<T> clazz) {
        Preconditions.checkNotNull(applicationContext);
        return applicationContext.getBean(beanName, clazz);
    }

    public static <T> T popBean(Class<T> clazz) {
        Preconditions.checkNotNull(applicationContext);
        return applicationContext.getBean(clazz);
    }

}
