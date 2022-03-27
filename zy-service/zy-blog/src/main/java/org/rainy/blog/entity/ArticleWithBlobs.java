package org.rainy.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.rainy.common.annotation.OperateIp;
import org.rainy.common.annotation.OperateTime;
import org.rainy.common.annotation.Operator;
import org.rainy.common.beans.AuditFieldListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author: zhangyu
 * @description: content和htmlContent这两个字段都是大文本类型的，所以单独新建一个包含这两个字段的实体，
 * 只有在需要使用到content和htmlContent这两个字段的时候才用这个实体，可以减少大文本的读写次数。
 * @date: in 2022/3/23 9:08 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Entity
@EntityListeners(value = AuditFieldListener.class)
@Table(name = "t_article")
public class ArticleWithBlobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "summary")
    private String summary;

    @Column(name = "content")
    private String content;

    @Column(name = "html_content")
    private String htmlContent;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "reading")
    private Long reading;

    @Column(name = "status")
    private Integer status;

    @Column(name = "seq")
    private Integer seq;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "likes")
    private Long likes;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Operator
    @Column(name = "operator")
    private Integer operator;

    @OperateTime
    @Column(name = "operate_time")
    private LocalDateTime operateTime;

    @OperateIp
    @Column(name = "operate_ip")
    private String operateIp;

}
