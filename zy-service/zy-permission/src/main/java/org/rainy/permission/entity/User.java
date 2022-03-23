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
    
    @Column(name = "registerTime")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(remark, user.remark) && Objects.equals(status, user.status) && Objects.equals(seq, user.seq) && Objects.equals(operator, user.operator) && Objects.equals(operateTime, user.operateTime) && Objects.equals(operateIp, user.operateIp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, remark, status, seq, operator, operateTime, operateIp);
    }
}