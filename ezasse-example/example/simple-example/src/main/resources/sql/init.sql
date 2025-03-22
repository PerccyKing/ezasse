# ezasse 会在数据库中查询是否有user表，没有的话会执行创建表
-- TABLE(user)
CREATE TABLE user
(
    id   BIGINT        NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    name VARCHAR(1024) NULL COMMENT '账号'
)
    COMMENT '用户表';


-- EXEC(select count(1) from user where id = 1)
INSERT INTO user(id, name) VALUE (1, 'ezasse');

