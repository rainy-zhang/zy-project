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
 * @date 2022/3/17 0017 20:20
 */
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 要回复的评论id
     */
    @Column(name = "reply_id")
    private Integer replyId;

    @Column(name = "blog_id")
    private Integer blogId;

    @Column(name = "content")
    private String content;

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
