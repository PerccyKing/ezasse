![logo](/doc/image/logo.svg)
# 快速入门
## 简介
ezasse(Easy automatic SQL script executor)一个简单的自动SQL执行器

[详细指南](https://ezasse.pism.com.cn)


### 立即体验(spring boot)
#### 1.安装

```xml
<dependency>
    <groupId>cn.com.pism.ezasse</groupId>
    <artifactId>ezasse-spring-boot-starter</artifactId>
    <version>0.0.4</version>
</dependency>
```

#### 2.配置
```yaml
spring:
  ezasse:
    #指定sql所在resource下的文件夹
    folder: sql
    #指定SQL执行顺序
    group-order:
      - initTable
      - updateTable
      - initData
```

#### 3.编写SQL文件
##### 1.initTable.sql
```sql
# ezasse 会在数据库中查询是否有user表，没有的话会执行创建表
-- TABLE(user)
CREATE TABLE user
(
    id          bigint                              NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    name     varchar(1024)                          NULL COMMENT '账号'
)
    COMMENT '用户表';
```
##### 2.updateTable.sql
```sql
# ezasse 会检查 user表中，是否存在user_type字段，如果没有会执行以下脚本
-- ADD(user.user_type)
alter table user
    add user_type varchar(1024) null comment '用户类型' after name;
```
##### 3.initData.sql
```sql
# ezasse 会检查EXEC(sql) 中的SQL 返回值，是否为0 如果结果为0 会执行以下脚本
-- EXEC(select count(1) from user where id = 1)
insert into user(id,name,user_type) value(1,'root','超级管理员');
```
#### 4.启动SpringBoot

### 贡献者
[![Contributor over time](https://contributor-overtime-api.git-contributor.com/contributors-svg?chart=contributorOverTime&repo=PerccyKing/ezasse)](https://git-contributor.com?chart=contributorOverTime&repo=PerccyKing/ezasse)
