package org.rainy.project.param;

import lombok.Data;
import org.rainy.project.entity.Log;
import org.springframework.beans.BeanUtils;

@Data
public class LogSearchParam {

    private Integer type;

    private Integer status;

    private String email;

    public Log convert() {
        Log log = new Log();
        BeanUtils.copyProperties(this, log);
        return log;
    }


}
