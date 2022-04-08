package org.rainy.project.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

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
