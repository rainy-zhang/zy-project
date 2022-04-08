package org.rainy.project.beans;

import com.google.common.collect.Lists;
import org.rainy.project.annotation.OperateIp;
import org.rainy.project.annotation.OperateTime;
import org.rainy.project.annotation.Operator;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Component
public class AuditFieldListener {

    /**
     * 插入数据前执行
     *
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
     *
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
