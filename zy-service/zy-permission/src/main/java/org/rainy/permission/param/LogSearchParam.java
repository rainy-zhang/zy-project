package org.rainy.permission.param;

import lombok.Data;
import org.rainy.permission.entity.Log;
import org.springframework.beans.BeanUtils;

/**
 * @description:
 * @author: wt1734
 * @date: 2021/11/10 14:54
 */
@Data
public class LogSearchParam {

    private Integer type;

    private Integer status;

    private String telephone;

    private String email;

    public Log convert() {
        Log log = new Log();
        BeanUtils.copyProperties(this, log);
        return log;
    }


}
