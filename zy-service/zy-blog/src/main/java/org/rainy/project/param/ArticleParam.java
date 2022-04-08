package org.rainy.project.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.rainy.project.entity.ArticleWithBlobs;
import org.rainy.project.beans.PageQuery;
import org.rainy.project.constant.ValidateGroups;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ArticleParam {

    @NotNull(message = "文章ID不能为空", groups = {ValidateGroups.UPDATE.class})
    private Integer id;

    @NotBlank(message = "标题不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    @Length(min = 1, max = 50, message = "标题长度需要在1-50之间", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private String title;

    @NotBlank(message = "摘要不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    @Length(min = 1, max = 100, message = "摘要长度需要在1-100之间", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private String summary;

    @NotBlank(message = "文章内容不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    @Length(min = 1, max = 5000, message = "文章内容长度需要在1-5000之间", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private String content;

    @NotNull(message = "请选择分类", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private Integer categoryId;

    @NotNull(message = "至少选择一个标签", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private List<Integer> tagIds;

    @NotNull(message = "文章分页参数不能为空", groups = {ValidateGroups.SELECT.class})
    private PageQuery articlePageQuery;

    private Integer status;

    public ArticleWithBlobs convert() {
        ArticleWithBlobs article = new ArticleWithBlobs();
        BeanUtils.copyProperties(this, article);
        return article;
    }

}
