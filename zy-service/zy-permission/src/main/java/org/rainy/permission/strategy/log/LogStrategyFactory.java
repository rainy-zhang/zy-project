package org.rainy.permission.strategy.log;

import org.rainy.permission.constant.LogType;
import org.rainy.common.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class LogStrategyFactory {

    private static List<LogStrategy> logStrategies;
    private static final Map<LogType, LogStrategy> strategyMap = new HashMap<>();

    @Autowired
    public void setLogStrategies(List<LogStrategy> strategies) {
        logStrategies = strategies;
    }

    public static LogStrategy getStrategy(Integer type) {
        LogType logType = LogType.types.get(type);
        LogStrategy logStrategy = strategyMap.get(logType);
        if (logStrategy == null) {
            Optional<LogStrategy> strategyOptional = logStrategies.stream().filter(strategy -> strategy.type() == logType).findAny();
            logStrategy = strategyOptional.orElseThrow(() -> new CommonException("unknown logType"));
            strategyMap.put(logType, logStrategy);
        }
        return logStrategy;
    }

}
