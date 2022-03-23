create table `zy-blog`.t_article
(
    id int not null
        primary key,
    title varchar(50) default '' not null comment '标题',
    summary varchar(100) default '' not null comment '摘要',
    content mediumtext not null comment '文章内容',
    html_content mediumtext not null comment 'html格式的文章内容',
    user_id int not null comment '发布人id',
    reading bigint not null comment '阅读量',
    likes bigint not null comment '点赞数',
    status int not null comment '博客状态，-1：删除、0：隐藏、1：正常、2：草稿',
    create_time datetime not null comment '创建时间',
    category_id int not null comment '分类id',
    operator int not null comment '操作人',
    operate_time datetime not null comment '操作时间',
    operate_ip varchar(20) default '0.0.0.0' not null comment '操作人IP地址'
)
    comment '文章表';

create table `zy-blog`.t_article_tag
(
    id int not null
        primary key,
    article_id int not null comment '文章ID',
    tag_id int not null comment '标签ID',
    operator int not null comment '操作人',
    operate_time datetime not null comment '操作时间',
    operate_ip varchar(20) default '' not null comment '操作人IP地址'
)
    comment '文章和标签关联表';

create table `zy-blog`.t_comment
(
    id int not null
        primary key,
    reply_id int not null comment '要回复的评论ID、如果不是回复其他评论则为空',
    article_id int not null comment '文章id',
    content varchar(200) default '' not null comment '评论内容',
    operator int not null comment '操作人',
    operate_time datetime not null comment '操作时间',
    operate_ip varchar(20) default '0.0.0.0' not null comment '操作人IP地址'
)
    comment '文章表';

create table `zy-blog`.t_follow
(
    id int not null
        primary key comment '用户ID',
    target_user_id int not null comment '目标用户ID',
    email int not null comment '邮箱',
    operator int not null comment '操作人',
    operate_time datetime not null comment '操作时间',
    operate_ip varchar(20) default '0.0.0.0' not null comment '操作人IP地址'
)
    comment '关注表';

create table `zy-blog`.t_message
(
    id int not null
        primary key,
    user_id int not null comment '用户ID',
    content varchar(250) default '' not null comment '留言内容',
    create_time datetime not null comment '创建时间',
    operator int not null comment '操作人',
    operate_time datetime not null comment '操作时间',
    operate_ip varchar(20) default '0.0.0.0' not null comment '操作人IP地址'
)
    comment '留言表';

create table `zy-blog`.t_tag
(
    id int not null primary key,
    name varchar(20) default '' not null comment '标签名称',
    remark varchar(200) default '' not null comment '备注',
    seq int not null comment '排序号',
    operator int not null comment '操作人',
    operate_time datetime not null comment '操作时间',
    operate_ip varchar(20) default '0.0.0.0' not null comment '操作人IP地址'
)
    comment '标签表';

create table `zy-blog`.t_category
(
    id int not null primary key,
    name varchar(20) default '' not null comment '分类名称',
    remark varchar(200) default '' not null comment '备注',
    seq int not null comment '排序号',
    operator int not null comment '操作人',
    operate_time datetime not null comment '操作时间',
    operate_ip varchar(20) default '0.0.0.0' not null comment '操作人IP地址'
)
    comment '分类表';

create table `zy-blog`.t_vote
(
    id int not null
        primary key,
    article_id int not null comment '文章ID',
    operator int not null comment '操作人',
    operate_time datetime not null comment '操作时间',
    operate_ip varchar(20) default '0.0.0.0' not null comment '操作人IP地址'
)
    comment '点赞表';








