package org.rainy.common.beans;

import org.assertj.core.util.Lists;
import org.rainy.common.annotation.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @description: 自动注入审计字段
 * @author: zhangyu
 * @date: in 2021/10/30 4:23 下午
 */
@Component
public class AuditFieldListener {

    /**
     * 插入数据前执行
     * @param entity
     * @throws IllegalAccessException
     */
    @PrePersist
    public static void insertAuditing(Object entity) throws IllegalAccessException {
        if (entity instanceof Collection) {
            Collection<?> collection = (Collection<?>) entity;
            for (Object target : collection) {
                doAuditing(target);
            }
            return;
        }
        doAuditing(entity);
    }

    /**
     * 更新数据前执行
     * @param entity
     * @throws IllegalAccessException
     */
    @PreUpdate
    public static void updateAuditing(Object entity) throws IllegalAccessException {
        if (entity instanceof Collection) {
            Collection<?> collection = (Collection<?>) entity;
            for (Object target : collection) {
                doAuditing(target);
            }
            return;
        }
        doAuditing(entity);
    }


    private static void doAuditing(Object entity) throws IllegalAccessException {
        for (Field field : getFields(entity)) {
            if (field.isAnnotationPresent(OperateTime.class)) {
                field.setAccessible(true);
                field.set(entity, LocalDateTime.now());
            }
            if (field.isAnnotationPresent(Operator.class)) {
                field.setAccessible(true);
                field.set(entity, -1);
            }
            if (field.isAnnotationPresent(OperateIp.class)) {
                field.setAccessible(true);
                field.set(entity, "127.0.0.1");
            }
        }
    }

    private static List<Field> getFields(Object entity) {
        Class<?> clazz = entity.getClass();
        List<Field> fields = Lists.newArrayList();
        while (clazz != null) {
            fields.addAll(List.of(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

}
