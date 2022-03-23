package org.rainy.permission.entity;

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
@Table(name = "sys_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status")
    private Integer status;

    @Column(name = "seq")
    private Integer seq;
    
    @Column(name = "register_time")
    private LocalDateTime registerTime;
    
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