-- TABLE(user)
-- 添加表
CREATE TABLE user
(
    id   BIGINT NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    name VARCHAR(1024) NULL COMMENT '账号'
) COMMENT '用户表';

# -- DO_CHANGE_COMMENT(user.id.主键的id)

-- DO_CHANGE_NAME(user.name.account)

-- DO_CHANGE_TYPE(user.account.varchar(100))

-- DO_CHANGE_LENGTH(user.account.1000)