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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Entity
@EntityListeners(value = AuditFieldListener.class)
@Table(name = "t_article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "summary")
    private String summary;

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

    @Column(name = "comments")
    private Long comments;
    
    @Column(name = "heat")
    private BigDecimal heat;
    
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

    public static class COLUMN {
        public static final String STATUS = "status";
        public static final String CATEGORY_ID = "categoryId";
        public static final String HEAD = "heat";
        
    }

}
