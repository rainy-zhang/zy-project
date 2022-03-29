package org.rainy.permission.strategy.log;

import org.rainy.permission.constant.LogType;

public interface LogStrategy {

    LogType type();

    /**
     * 回滚修改操作，将原来的数据再改回去
     *
     * @param beforeValue
     * @param afterValue
     */
    void recoveryUpdate(String beforeValue, String afterValue);

    /**
     * 回滚删除操作，将原来的数据重新插入
     *
     * @param beforeValue
     */
    void recoveryDelete(String beforeValue);

    /**
     * 回滚新增操作，删除当前数据
     *
     * @param afterValue
     */
    void recoveryInsert(String afterValue);

}
