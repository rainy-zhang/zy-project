package org.rainy.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.rainy.project.annotation.OperateIp;
import org.rainy.project.annotation.OperateTime;
import org.rainy.project.annotation.Operator;
import org.rainy.project.beans.AuditFieldListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_log")
@EntityListeners(value = AuditFieldListener.class)
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type")
    private Integer type;

    @Column(name = "status")
    private Integer status;

    @Column(name = "target_id")
    private Integer targetId;

    @Column(name = "before")
    private String before;

    @Column(name = "after")
    private String after;

    @Column(name = "op_type")
    private Integer opType;

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