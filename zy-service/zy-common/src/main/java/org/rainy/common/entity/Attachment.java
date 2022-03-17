package org.rainy.common.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.rainy.common.beans.AuditFieldListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * <p>附件实体类</p>
 * @author wt1734
 * @date 2021/11/24 0024
 */
@Data
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "t_attachment")
@EntityListeners(value = AuditFieldListener.class)
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Integer type;

    @Column(name = "material_type")
    private Integer materialType;

    @Column(name = "url")
    private String url;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "operator")
    private Integer operator;

    @Column(name = "operate_time")
    private LocalDateTime operateTime;

    @Column(name = "operate_ip")
    private String operateIp;

}
