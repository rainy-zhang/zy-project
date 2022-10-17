package org.rainy.project.strategy.log;


public abstract class AbstractLogStrategy implements LogStrategy {
    
    protected void register() {
        LogStrategyContext.registerStrategy(type(), this);
    }
    
}
