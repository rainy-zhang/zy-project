package org.rainy.common.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
public class CommonUtils {

    public static String ago(LocalDateTime conditionTime) {
        Preconditions.checkNotNull(conditionTime, "条件日期不能为空");
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(conditionTime, now);
        if (duration.isNegative()) {
            throw new IllegalArgumentException("conditionTime > currentTime");
        }
        long days = duration.toDays();
        long hours = duration.toHours();
        long minutes = duration.toMinutes();
        long seconds = duration.toSeconds();
        if (days > 0) {
            return String.format("%d day ago", days);
        } else if (hours > 0) {
            return String.format("%d hour ago", hours);
        } else if (minutes > 0) {
            return String.format("%d min ago", minutes);
        } else if (seconds > 0) {
            return String.format("%d second ago", seconds);
        } else {
            return "just now";
        }
    }

    public static <T> Map<String, Object> object2Map(T entity) {
        Map<String, Object> beanMap = Maps.newHashMap();
        try {
            Field[] fields = entity.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                beanMap.put(field.getName(), field.get(entity));
            }
        } catch (IllegalAccessException e) {
            log.error("object to map convert error, ", e);
        }
        return beanMap;
    }

}
