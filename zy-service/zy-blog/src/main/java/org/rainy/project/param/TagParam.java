package org.rainy.project.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.rainy.project.entity.Tag;
import org.rainy.project.constant.ValidateGroups;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

@Data
public class TagParam {

    @NotNull(message = "标签ID不能为空", groups = {ValidateGroups.UPDATE.class})
    private Integer id;

    @NotNull(message = "标签名称不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    @Length(min = 1, max = 10, message = "标签名称长度在1-10个字符之间", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private String name;

    @Length(min = 1, max = 100, message = "备注长度在1-100个字符之间", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private String remark;

    public Tag convert() {
        Tag tag = new Tag();
        BeanUtils.copyProperties(this, tag);
        return tag;
    }

}
