-- iRunner 项目数据库初始化脚本
-- 数据库: lyc

-- ----------------------------
-- 1. 用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                        `username` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
                        `useraccount` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登录账号',
                        `avatarurl` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户头像',
                        `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
                        `userpassword` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
                        `email` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
                        `userstatus` int(11) NOT NULL DEFAULT '0' COMMENT '用户状态 0-正常',
                        `createtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `updatetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除(逻辑删除)',
                        `userrole` int(11) NOT NULL DEFAULT '0' COMMENT '用户角色 0-普通用户 1-管理员',
                        `phone` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uni_userAccount` (`useraccount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';


-- ----------------------------
-- 2. 运动记录表
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
                            `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                            `distance` decimal(10,2) NOT NULL COMMENT '距离(公里)',
                            `duration` int(11) NOT NULL COMMENT '时长(秒)',
                            `start_time` datetime NOT NULL COMMENT '开始时间',
                            `avg_speed` decimal(10,2) DEFAULT NULL COMMENT '平均配速(分钟/公里)',
                            `calories` int(11) DEFAULT NULL COMMENT '消耗卡路里',
                            `track_points` longtext COLLATE utf8mb4_unicode_ci COMMENT '轨迹点 (JSON格式)',
                            `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`),
                            KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='运动记录表';


-- ----------------------------
-- 3. 社区动态表
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '动态ID',
                        `user_id` bigint(20) NOT NULL COMMENT '发布者ID',
                        `activity_id` bigint(20) DEFAULT NULL COMMENT '关联的运动记录ID (可以为空)',
                        `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '动态文字内容',
                        `image_urls` text COLLATE utf8mb4_unicode_ci COMMENT '图片URL列表 (存储为JSON数组)',
                        `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`),
                        KEY `idx_user_id` (`user_id`),
                        KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='社区动态表';
