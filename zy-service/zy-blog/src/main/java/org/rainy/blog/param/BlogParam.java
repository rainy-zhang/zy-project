package org.rainy.blog.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.rainy.blog.entity.Blog;
import org.rainy.common.constant.ValidateGroups;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

@Data
public class BlogParam {
    
    @NotBlank(message = "标题不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    @Length(min = 1, max = 50, message = "标题长度需要在1-50之间", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private String title;

    @NotBlank(message = "摘要不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    @Length(min = 1, max = 100, message = "摘要长度需要在1-100之间", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private String summary;

    @NotBlank(message = "内容不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    @Length(min = 1, max = 5000, message = "内容长度需要在1-5000之间", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private String content;
    
    public Blog convert() {
        Blog blog = new Blog();
        BeanUtils.copyProperties(this, blog);
        return blog;
    }
    
}
