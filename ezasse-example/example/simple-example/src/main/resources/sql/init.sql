# ezasse 会在数据库中查询是否有user表，没有的话会执行创建表
-- TABLE(user)
-- 添加表
CREATE TABLE user
(
    id   BIGINT        NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    name VARCHAR(1024) NULL COMMENT '账号'
)
    COMMENT '用户表';

-- ADD(user.new_column)
-- 添加新字段
ALTER TABLE user
    ADD new_column VARCHAR(20) NULL COMMENT '新字段';
INSERT INTO user(id, name) VALUE (2, 'ezasse2');

-- EXEC(select count(1) from user where id = 1)
-- 当不存在user.id为1的数据执行
INSERT INTO user(id, name) VALUE (1, 'ezasse');

-- CHANGE_NAME(user.name.new_name)
-- 将字段name修改为new_name
ALTER TABLE user
    CHANGE name new_name VARCHAR(1024) NULL COMMENT '账号';

-- CHANGE_TYPE(user.new_name.TEXT)
-- 将字段new_name的数据类型修改为TEXT
ALTER TABLE user
    MODIFY new_name TEXT NULL COMMENT '账号';

-- CHANGE_LENGTH(user.new_column.255)
-- 将字段new_column 的数据长度修改为255
ALTER TABLE user
    MODIFY new_column VARCHAR(255) NULL COMMENT '新字段255';

-- CHANGE_COMMENT(user.new_column.新字段new)
-- 将字段new_column的注释修改为 新字段
ALTER TABLE user
    MODIFY new_column VARCHAR(255) NULL COMMENT '新字段new';
