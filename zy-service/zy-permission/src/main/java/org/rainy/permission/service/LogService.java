package org.rainy.permission.service;

import com.google.common.base.Preconditions;
import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.common.constant.CommonStatus;
import org.rainy.common.exception.BeanNotFoundException;
import org.rainy.common.exception.IllegalBeanException;
import org.rainy.common.util.JsonMapper;
import org.rainy.permission.constant.LogOpType;
import org.rainy.permission.constant.LogType;
import org.rainy.permission.entity.Log;
import org.rainy.permission.param.LogSearchParam;
import org.rainy.permission.repository.LogRepository;
import org.rainy.permission.strategy.log.LogStrategy;
import org.rainy.permission.strategy.log.LogStrategyFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: wt1734
 * @date: 2021/11/10 14:32
 */
@Service
public class LogService {

    private final LogRepository logRepository;
    private final Executor executor;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
        this.executor = Executors.newFixedThreadPool(10);
    }

    /**
     * 日志分页查询
     *
     * @param searchParam
     * @param pageQuery
     * @return
     */
    public PageResult<Log> findLogs(LogSearchParam searchParam, PageQuery pageQuery) {
        Log log = searchParam.convert();
        Example<Log> example = Example.of(log);
        Pageable pageable = pageQuery.convert();
        return PageResult.of(logRepository.findAll(example, pageable));
    }

    /**
     * 回滚
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void rollback(Integer id) {

        Log log = logRepository.findById(id).orElseThrow(() -> new BeanNotFoundException("log not found"));
        if (CommonStatus.INVALID.getCode() == log.getStatus()) {
            throw new IllegalBeanException("log can not rollback");
        }

        LogOpType opType = LogOpType.opTypes.get(log.getOpType());
        LogStrategy logStrategy = LogStrategyFactory.getStrategy(log.getType());
        switch (opType) {
            case INSERT:
                logStrategy.recoveryInsert(log.getAfter());
                break;
            case DELETE:
                logStrategy.recoveryDelete(log.getBefore());
                break;
            case UPDATE:
                logStrategy.recoveryUpdate(log.getBefore(), log.getAfter());
                break;
        }
        log.setStatus(CommonStatus.INVALID.getCode());
        save(log);
    }

    // 多线程保存日志，避免影响其它接口执行效率
    @Transactional(rollbackFor = Exception.class)
    public void save(Object before, Object after, LogType logType, LogOpType opType) {
        executor.execute(() -> {
            Object targetId = null;
            if (before != null) {
                targetId = getPrimaryKey(before);
            }
            if (after != null) {
                targetId = getPrimaryKey(after);
            }
            Preconditions.checkNotNull(targetId, "bean not found @Id annotation");
            Log log = Log.builder()
                    .type(logType.getCode())
                    .targetId(Integer.parseInt(targetId.toString()))
                    .status(CommonStatus.VALID.getCode())
                    .after(JsonMapper.object2String(after))
                    .before(JsonMapper.object2String(before))
                    .opType(opType.getCode())
                    .build();
            save(log);
        });
    }

    /**
     * 获取实体类中带有{@link Id}注解的属性作为主键
     *
     * @param entity
     * @return
     */
    private Object getPrimaryKey(Object entity) {
        Object id = null;
        try {
            Class<?> clazz = entity.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    id = field.get(entity);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 保存日志
     *
     * @param log
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(Log log) {
        logRepository.save(log);
    }


}
