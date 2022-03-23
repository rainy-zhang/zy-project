package org.rainy.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.rainy.common.annotation.OperateIp;
import org.rainy.common.annotation.OperateTime;
import org.rainy.common.annotation.Operator;
import org.rainy.common.beans.AuditFieldListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Entity
@EntityListeners(value = AuditFieldListener.class)
@Table(name = "t_article_tag")
public class ArticleTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "tag_id")
    private Integer tagId;

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
