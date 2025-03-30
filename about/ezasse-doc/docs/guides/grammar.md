# 校验语法

## 规则

```sql
-- 关键字[.校验节点][.执行节点](校验内容)
```

所有校验行需要以`--`或`#`开头，当前暂时没有开放配置

* ##### 关键字

***必要***
关键字对应着不同的校验功能，使用不同的校验关键字，会选取不同的校验器

* ##### 校验节点

***可选的***
校验节点为数据源名称，如果指定了校验节点，校验内容会在该节点中执行，如果只指定了校验节点，校验内容与执行内容都在该节点中执行

* ##### 执行节点

***可选的***
执行节点为数据源名称，要先有校验节点，执行节点才生效，指定了执行节点，执行内容在指定的执行节点中执行

* ##### 校验内容

***必要***
校验内容按照关键字规则进行添加一般格式类似于`(xxx.xxx.xxx)`，不同的校验关键字，校验内容编写规则也有所不同

## 通用校验

> 起始界定符设置为 `-- [`，`-- ]`

* ### EXEC

#### 语法

```sql
-- EXEC(校验语句)
sql
```

#### 解释

作用：当在校验节点的库中，执行括号中的校验SQL，如果校验SQL返回值为0，则会执行SQL脚本

EXEC 括号中的内容应为一个SQL语句，当然也可以自定义校验器
，让校验内容为非SQL，sql脚本内容未做限制，可以为任何内容

#### 使用示例

1. 如果SQL文件没有指定校验节点，会在master节点执行校验语句，通过后，在master节点执行代码块

```sql
-- EXEC(select 0)
INSERT INTO table_1(id, name) value(1,'张三');
INSERT INTO table_1(id, name) value(2,'李四');
```

2. 多数据源使用，校验语句将在slave节点执行，如果校验通过，SQL将在slave节点执行

```sql
-- EXEC.slave(select 0);
INSERT INTO table_1(id, name) value(1,'张三');
INSERT INTO table_1(id, name) value(2,'李四');
```

3. 多数据源使用，校验语句将在slave节点执行，如果校验通过，SQL将在master节点执行

```sql
-- EXEC.slave.master(select 0);
INSERT INTO table_1(id, name) value(1,'张三');
INSERT INTO table_1(id, name) value(2,'李四');
```

## 表操作

### TABLE

#### 语法

```sql
-- TABLE(表名)
sql
```

#### 解释

作用：当在校验节点的库中，没有找到与`表名`相关的信息，则会执行SQL脚本

TABLE括号中的内容为`表名`，sql内容没有限制为只能是创建表语句，但是建议是创建表语句

#### 使用示例

```sql
-- TABLE(t_user)
create table t_user
(
    id   varchar(60) not null comment '主键id'
        primary key,
    name varchar(255) null comment '用户名'
) comment '用户表';

```

## 表属性/字段操作

### ADD

#### 语法

```sql
-- ADD(表名.列名)
sql
```

#### 解释

作用：当在校验节点的库中，没有找到与`表名`和`列名`相关的信息，则会执行SQL脚本

ADD的括号中有两个参数，分别为 `表名`和`列名`，sql没有限定为添加列的语句，但是建议为添加列的语句

#### 使用示例

在`t_user`表中，添加字段 `age`

```sql
#
当t_user表中没有age字段时
，会执行以下SQL
-- ADD(t_user.age)
ALTER TABLE t_user
    ADD age varchar(3) NULL COMMENT '年龄';
```

### CHANGE_NAME

#### 语法

```sql
-- CHANGE_NAME(表名.列名)
sql
```

#### 解释

作用：当在校验节点的库中，没有找到与`表名`和`修改后的列名`相关的信息，则会执行SQL脚本

CHANGE_NAME的括号中有两个参数，分别为`表名`和`修改后的列名`，sql没有限定为修改列名，但是建议为修改列表的语句

#### 使用示例

将`t_user`表中的`name`字段的名称修改为`u_name`

```sql
-- CHANGE_NAME(t_user.u_name)
ALTER TABLE t_user
    CHANGE name u_name varchar (255) NULL COMMENT '用户名';
```

### CHANGE_TYPE

#### 语法

```sql
-- CHANGE_TYPE(表名.需要修改类型的列名.修改后的类型)
sql
```

#### 解释

作用：当在校验节点的库中，`表名`中的`需要修改类型的列名`字段的数据类型，不是`修改后的数据类型`，则会执行SQL脚本

CHANGE_TYPE的括号中有三个参数，分别为`表名`和`需要修改类型的列名`、`修改后的数据类型`，sql没有限定为修改列的数据类型的语句，但是建议为修改列的数据类型的语句

#### 使用示例

将`t_user`表中的`age`字段的数据类型修改为`int`

```sql
-- CHANGE_TYPE(t_user.age.int)
alter table t_user
    modify age int null comment '年龄';
```

### CHANGE_COMMENT

#### 语法

```sql
-- CHANGE_COMMENT(表名.需要修改备注的列名.修改后的备注信息)
sql
```

#### 解释

作用：当在校验节点的库中，`表名`中的`需要修改备注的列名`字段的备注，不是`修改后的备注信息`，则会执行SQL脚本

CHANGE_COMMENT的括号中有三个参数，分别为`表名`和`需要修改备注的列名`、`修改后的备注信息`
，sql没有限定为修改列的备注信息的语句，但是建议为修改列的备注信息的语句

#### 使用示例

将`t_user`表中的`age`字段的comment 修改为 `实际年龄`

```sql
-- CHANGE_COMMENT(t_user.age.实际年龄)
ALTER TABLE t_user
    MODIFY age int NULL COMMENT '实际年龄';
```

### CHANGE_LENGTH

#### 语法

```sql
-- CHANGE_LENGTH(表名.需要修改长度的列名.修改后的长度)
sql
```

#### 解释

作用：当在校验节点的库中，`表名`中的`需要修改长度的列名`字段的备注，不是`修改后的长度`，则会执行SQL脚本

CHANGE_LENGTH的括号中有三个参数，分别为`表名`和`需要修改长度的列名`、`修改后的长度`，sql没有限定为修改列的数据长度的语句，但是建议为修改列的数据长度的语句

#### 使用示例

将`t_user`表中的`u_name`字段的长度修改为`1024`

```sql
-- CHANGE_LENGTH(t_user.u_name.1024)
ALTER TABLE t_user
    MODIFY u_name varchar (1024) NULL COMMENT '用户名';
```
