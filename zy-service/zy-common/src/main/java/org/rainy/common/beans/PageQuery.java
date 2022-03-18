package org.rainy.common.beans;

import lombok.Getter;
import lombok.Setter;
import org.rainy.common.constant.ValidateGroups;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Min;

@Getter
@Setter
public class PageQuery {

    @Min(value = 1, message = "当前页码不合法", groups = ValidateGroups.SELECT.class)
    private int pageNo;

    @Min(value = 1, message = "每页展示的数量不合法", groups = ValidateGroups.SELECT.class)
    private int pageSize;

    public Pageable convert() {
        return PageRequest.of(this.pageNo - 1, this.pageSize);
    }

}
