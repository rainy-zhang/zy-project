package org.rainy.blog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.rainy.common.annotation.OperateIp;
import org.rainy.common.annotation.OperateTime;
import org.rainy.common.annotation.Operator;
import org.rainy.common.beans.AuditFieldListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
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
    
    public static class COLUMN {
        public static final String STATUS = "status";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id) && Objects.equals(title, article.title) && Objects.equals(summary, article.summary) && Objects.equals(content, article.content) && Objects.equals(htmlContent, article.htmlContent) && Objects.equals(userId, article.userId) && Objects.equals(reading, article.reading) && Objects.equals(status, article.status) && Objects.equals(categoryId, article.categoryId) && Objects.equals(likes, article.likes) && Objects.equals(createTime, article.createTime) && Objects.equals(operator, article.operator) && Objects.equals(operateTime, article.operateTime) && Objects.equals(operateIp, article.operateIp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, summary, content, htmlContent, userId, reading, status, categoryId, likes, createTime, operator, operateTime, operateIp);
    }
}
