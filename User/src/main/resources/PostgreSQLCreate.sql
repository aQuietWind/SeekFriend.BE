CREATE DATABASE if not exists  seek_friend_user;
USE seek_friend_user;
drop table if exists  `t_user`;
CREATE TABLE `t_user` (
                        `user_id` bigint NOT NULL COMMENT '用户id',
                        `username` varchar(15) NOT NULL COMMENT '用户名',
                        `phone_number` varchar(30) NOT NULL UNIQUE COMMENT '手机号',
                        `password` varchar(20) NOT NULL COMMENT '密码',
                        `sex` tinyint COMMENT '性别',
                        `header_image_addr` varchar(50) COMMENT '头像地址',
                        `birthday` date COMMENT '生日',
                        `user_friend_amount` int NOT NULL DEFAULT 0 COMMENT '正常的用户朋友量',
                        `ai_friend_amount` int NOT NULL DEFAULT 0 COMMENT 'AI朋友量',
                        `create_time` datetime NOT NULL default now() COMMENT '创建时间',
                        `is_delete` boolean NOT NULL DEFAULT false COMMENT '是否被删除',
                        PRIMARY KEY (`user_id`),
                        UNIQUE KEY `uk_phone` (`phone_number`),
                        UNIQUE KEY `uk_header_image_addr`(`header_image_addr`),
                        INDEX phone_index(`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';































