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
            return String.format("%d day ago", days);
        } else if (hours > 0) {
            return String.format("%d hour ago", days);
        } else if (minutes > 0) {
            return String.format("%d min ago", days);
        } else if (seconds > 0) {
            return String.format("%d second ago", days);
        } else {
            return "just now";
        }
    }

}
