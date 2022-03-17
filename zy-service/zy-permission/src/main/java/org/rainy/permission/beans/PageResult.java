package org.rainy.permission.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @description: 返回分页结果
 * @author: zhangyu
 * @date: in 2021/10/30 11:27 上午
 */
@Data
@Builder
@AllArgsConstructor
public class PageResult<T> {

    private int total;
    private List<T> data;

    public static <T> PageResult<T> of(Page<T> page) {
        return new PageResult<>(page.getSize(), page.getContent());
    }

}
