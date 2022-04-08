package org.rainy.project.entity;

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
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(value = AuditFieldListener.class)
@Table(name = "sys_acl_module")
public class AclModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "seq")
    private Integer seq;

    @Column(name = "status")
    private Integer status;

    @Column(name = "remark")
    private String remark;

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