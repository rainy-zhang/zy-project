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
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(value = AuditFieldListener.class)
@Table(name = "sys_role_acl")
public class RoleAcl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "acl_id")
    private Integer aclId;

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