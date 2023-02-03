-- 用户表
--  Drop table
--  DROP TABLE demo.`sysUserInfo`;
CREATE TABLE `sys_user_info` (
                                 `id` bigint(20) NOT NULL,
                                 `username` varchar(100) NOT NULL COMMENT '用户名',
                                 `password` varchar(100) NOT NULL COMMENT '用户密码',
                                 PRIMARY KEY (`id`),
                                 KEY `user_username_IDX` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8


CREATE TABLE `sys_role_info` (
                                 `id` varchar(100) DEFAULT NULL,
                                 `role_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表'

CREATE TABLE `sys_menu_info` (
                                 `id` varchar(100) DEFAULT NULL,
                                 `menu_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单'


CREATE TABLE `sys_user_role` (
                                 `id` varchar(100) DEFAULT NULL,
                                 `user_id` varchar(100) DEFAULT NULL,
                                 `role_id` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色用户表'

CREATE TABLE `sys_role_menu` (
                                 `id` varchar(100) DEFAULT NULL,
                                 `role_id` varchar(100) DEFAULT NULL,
                                 `menu_id` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单表'

-- 产品表

CREATE TABLE `product` (
                           `pid` varchar(100) NOT NULL,
                           `product_name` varchar(100) NOT NULL COMMENT '产品名称',
                           `price` varchar(100) DEFAULT NULL COMMENT '产品价格',
                           PRIMARY KEY (`pid`),
                           KEY `product_pid_IDX` (`pid`) USING BTREE,
                           KEY `product_productName_IDX` (`product_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
