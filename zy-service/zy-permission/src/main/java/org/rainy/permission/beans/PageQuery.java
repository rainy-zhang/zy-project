package org.rainy.permission.beans;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Min;

/**
 * @description: 分页请求参数
 * @author: zhangyu
 * @date: in 2021/10/30 10:27 上午
 */
@Getter
@Setter
public class PageQuery {

    @Min(value = 1, message = "当前页码不合法")
    private int pageNo;

    @Min(value = 1, message = "每页展示的数量不合法")
    private int pageSize;

    public Pageable convert() {
        return PageRequest.of(this.pageNo - 1, this.pageSize);
    }


}
