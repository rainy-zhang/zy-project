package org.rainy.blog.entity;

import org.rainy.common.annotation.OperateIp;
import org.rainy.common.annotation.OperateTime;
import org.rainy.common.annotation.Operator;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author wt1734
 * @date 2022/3/17 0017 20:10
 */
public class BlogTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private Integer blogId;
    
    private Integer tagId;

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
