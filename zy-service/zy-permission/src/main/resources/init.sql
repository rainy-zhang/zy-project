
-- ----------------------------
-- Table structure for sys_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl`;
CREATE TABLE `sys_acl` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `name` varchar(15) NOT NULL DEFAULT '' COMMENT '权限点名称',
                           `url` varchar(100) NOT NULL DEFAULT '' COMMENT '请求地址',
                           `acl_module_id` int NOT NULL DEFAULT '-1' COMMENT '权限模块ID',
                           `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
                           `status` int NOT NULL DEFAULT '1' COMMENT '状态码：0：无效，1：有效',
                           `seq` int NOT NULL DEFAULT '0' COMMENT '排序号(从小到大)',
                           `operator` int NOT NULL  DEFAULT '1' COMMENT '操作者',
                           `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
                           `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一个更新者的ip地址',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限点表';

-- ----------------------------
-- Table structure for sys_acl_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl_module`;
CREATE TABLE `sys_acl_module` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `name` varchar(15) NOT NULL DEFAULT '' COMMENT '权限模块名称',
                                  `parent_id` int NOT NULL DEFAULT '-1' COMMENT '上级权限模块',
                                  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
                                  `status` int NOT NULL DEFAULT '1' COMMENT '状态码：0：无效，1：有效',
                                  `seq` int NOT NULL DEFAULT '0' COMMENT '排序号(从小到大)',
                                  `operator` int NOT NULL  DEFAULT '1' COMMENT '操作者',
                                  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
                                  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一个更新者的ip地址',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限模块表';

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `type` int NOT NULL DEFAULT '0' COMMENT '功能类型：1：用户，2：角色，3：权限点，4：权限模块，5：角色与用户关联关系，5：角色与权限点关联关系',
                                   `status` int NOT NULL DEFAULT '0' COMMENT '状态码：0：无效，1：有效',
                                   `target_id` int NOT NULL DEFAULT '-1' COMMENT '目标表ID',
                                   `before` text COMMENT '操作前的值',
                                   `after` text COMMENT '操作后的值',
                                   `op_type` int NOT NULL DEFAULT '0' COMMENT '操作类型：1：新增，2：删除, 3:修改',
                                   `operator` int NOT NULL  DEFAULT '1' COMMENT '操作者',
                                   `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
                                   `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一个更新者的ip地址',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统操作日志表';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(15) NOT NULL DEFAULT '' COMMENT '角色名称',
                            `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
                            `status` int NOT NULL DEFAULT '1' COMMENT '状态码：0：无效，1：有效',
                            `seq` int NOT NULL DEFAULT '0' COMMENT '排序号(从小到大)',
                            `operator` int NOT NULL  DEFAULT '1' COMMENT '操作者',
                            `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
                            `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一个更新者的ip地址',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

-- ----------------------------
-- Table structure for sys_role_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_acl`;
CREATE TABLE `sys_role_acl` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `role_id` int NOT NULL DEFAULT '-1' COMMENT '角色ID',
                                `acl_id` int NOT NULL DEFAULT '-1' COMMENT '权限点ID',
                                `operator` int NOT NULL  DEFAULT '1' COMMENT '操作者',
                                `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
                                `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一个更新者的ip地址',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色与权限点关联关系表';

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `role_id` int NOT NULL DEFAULT '-1' COMMENT '角色ID',
                                 `user_id` int NOT NULL DEFAULT '-1' COMMENT '角色ID',
                                 `operator` int NOT NULL DEFAULT '1'  COMMENT '操作者',
                                 `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
                                 `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一个更新者的ip地址',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色与用户关联关系表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
                            `nickname` varchar(20) NOT NULL DEFAULT '' COMMENT '昵称',
                            `email` varchar(30) NOT NULL DEFAULT '' COMMENT '邮箱',
                            `telephone` varchar(18) NOT NULL DEFAULT '' COMMENT '手机号',
                            `password` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
                            `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
                            `status` int NOT NULL DEFAULT '1' COMMENT '状态码：0：无效，1：有效',
                            `seq` int NOT NULL DEFAULT '0' COMMENT '排序号(从小到大)',
                            `operator` int NOT NULL  DEFAULT '1' COMMENT '操作者',
                            `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
                            `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一个更新者的ip地址',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';


SET FOREIGN_KEY_CHECKS = 1;
