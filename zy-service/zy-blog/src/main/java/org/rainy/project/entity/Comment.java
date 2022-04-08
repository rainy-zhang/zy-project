package org.rainy.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.rainy.project.annotation.OperateIp;
import org.rainy.project.annotation.OperateTime;
import org.rainy.project.annotation.Operator;
import org.rainy.project.beans.AuditFieldListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Entity
@EntityListeners(value = AuditFieldListener.class)
@Table(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 要回复的评论id
     */
    @Column(name = "reply_id")
    private Integer replyId;

    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private Integer status;

    @Operator
    @Column(name = "operator")
    private Integer operator;

    @OperateTime
    @Column(name = "operate_time")
    private LocalDateTime operateTime;

    @OperateIp
    @Column(name = "operate_ip")
    private String operateIp;

    public static class COLUMN {
        public static final String ARTICLE_ID = "articleId";
    }


}
