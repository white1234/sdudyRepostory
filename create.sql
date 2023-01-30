-- 用户表
--  Drop table
--  DROP TABLE demo.`user`;
CREATE TABLE demo.`user` (
                             uid varchar(100) NOT NULL,
                             username varchar(100) NOT NULL COMMENT '用户名',
                             password varchar(100) NOT NULL COMMENT '用户密码',
                             CONSTRAINT user_PK PRIMARY KEY (uid)
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;
CREATE INDEX user_username_IDX USING BTREE ON demo.`user` (username);


-- 产品表
--  Drop table
--  DROP TABLE demo.`product`;
CREATE TABLE demo.product (
                              pid varchar(100) NOT NULL,
                              product_name varchar(100) NOT NULL COMMENT '产品名称',
                              price varchar(100) NULL COMMENT '产品价格',
                              CONSTRAINT product_PK PRIMARY KEY (pid)
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;
CREATE INDEX product_pid_IDX USING BTREE ON demo.product (pid);
CREATE INDEX product_productName_IDX USING BTREE ON demo.product (product_name);
