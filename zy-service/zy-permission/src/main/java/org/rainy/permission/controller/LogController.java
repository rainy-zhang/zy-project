package org.rainy.permission.controller;

import org.rainy.common.beans.PageQuery;
import org.rainy.common.beans.PageResult;
import org.rainy.permission.entity.Log;
import org.rainy.permission.param.LogSearchParam;
import org.rainy.permission.service.LogService;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: wt1734
 * @date: 2021/11/10 19:31
 */
@RestController
@RequestMapping(value = "/log")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    /**
     * 系统操作日志查询接口
     * @param searchParam
     * @param pageQuery
     * @return
     */
    @GetMapping(value = "/search")
    public PageResult<Log> search(@RequestBody LogSearchParam searchParam, @RequestBody PageQuery pageQuery) {
        return logService.findLogs(searchParam, pageQuery);
    }

    /**
     * 回滚日志
     * @param id
     */
    @PutMapping(value = "/rollback")
    public void rollback(@RequestParam(value = "id") Integer id) {
        logService.rollback(id);
    }

}
