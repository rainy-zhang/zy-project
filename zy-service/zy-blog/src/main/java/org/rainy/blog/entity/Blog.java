package org.rainy.blog.entity;

import org.rainy.common.annotation.OperateIp;
import org.rainy.common.annotation.OperateTime;
import org.rainy.common.annotation.Operator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author wt1734
 * @date 2022/3/17 0017 20:09
 */
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "summary")
    private String summary;

    @Column(name = "content")
    private String content;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "reading")
    private Long reading;

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
    
}
