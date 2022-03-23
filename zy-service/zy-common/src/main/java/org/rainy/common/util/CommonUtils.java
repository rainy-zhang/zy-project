package org.rainy.common.util;

import com.google.common.base.Preconditions;

import java.time.Duration;
import java.time.LocalDateTime;

public class CommonUtils {

    public static String ago(LocalDateTime conditionTime) {
        Preconditions.checkNotNull(conditionTime, "条件日期不能为空");
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(conditionTime, now);
        if (duration.isNegative()) {
            throw new IllegalArgumentException("conditionTime > currentTime");
        }
        String unit;
        long days = duration.toDays();
        long hours = duration.toHours();
        long minutes = duration.toMinutes();
        long seconds = duration.toSeconds();
        if (days > 0) {
            unit = "天";
        } else if (hours > 0) {
            unit = "小时";
        } else if (minutes > 0) {
            unit = "分钟";
        } else if (seconds > 0) {
            unit = "秒";
        } else {
            return "刚刚";
        }
        return String.format("%d%s前", days, unit);
    }

}
