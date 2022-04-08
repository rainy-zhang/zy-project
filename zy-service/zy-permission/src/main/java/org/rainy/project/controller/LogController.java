package org.rainy.project.controller;

import org.rainy.project.beans.PageQuery;
import org.rainy.project.beans.PageResult;
import org.rainy.project.entity.Log;
import org.rainy.project.param.LogSearchParam;
import org.rainy.project.service.LogService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/log")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    /**
     * 系统操作日志查询接口
     *
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
     *
     * @param id
     */
    @PutMapping(value = "/rollback")
    public void rollback(@RequestParam(value = "id") Integer id) {
        logService.rollback(id);
    }

}
