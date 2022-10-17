package org.rainy.project.strategy.log;

import org.rainy.project.constant.LogType;

import java.util.HashMap;
import java.util.Map;

public class LogStrategyContext {

    public static final Map<LogType, LogStrategy> strategies = new HashMap<>();

    public static LogStrategy getStrategy(Integer type) {
        return strategies.get(LogType.types.get(type));
    }

    public static void registerStrategy(LogType logType, LogStrategy logStrategy) {
        strategies.putIfAbsent(logType, logStrategy);
    }

}
