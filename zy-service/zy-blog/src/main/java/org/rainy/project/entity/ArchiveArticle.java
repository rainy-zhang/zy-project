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
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Entity
@EntityListeners(value = AuditFieldListener.class)
@Table(name = "t_archive_article")
public class ArchiveArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "archive_id")
    private Integer archiveId;

    @Column(name = "article_id")
    private Integer articleId;

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
