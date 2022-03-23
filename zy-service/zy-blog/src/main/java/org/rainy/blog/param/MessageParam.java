package org.rainy.blog.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.rainy.blog.entity.Message;
import org.rainy.common.constant.ValidateGroups;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class MessageParam {
    
    @NotNull(message = "消息ID不能为空", groups = {ValidateGroups.UPDATE.class})
    private Integer id;

    @NotNull(message = "内容不能为空", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    @Length(min = 1, max = 250, message = "内容长度在1-250个字符之间", groups = {ValidateGroups.INSERT.class, ValidateGroups.UPDATE.class})
    private String content;

    @NotNull(message = "用户ID不能为空", groups = {ValidateGroups.UPDATE.class})
    private Integer userId;
    
    @NotNull(message = "创建时间不能为空", groups = {ValidateGroups.UPDATE.class})
    private LocalDateTime createTime;
    
    public Message convert() {
        Message message = new Message();
        BeanUtils.copyProperties(this, message);
        return message;
    }
    
}
