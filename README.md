# 快速入门
## 简介
ezasse(Easy automatic SQL script executor)一个简单的自动SQL执行器
## 立即体验
```text
resources
    文件夹1_001
        file.sql
        file-001.sql
        file-001-master.sql
        file-001-master-user.sql
        文件夹3
            file-001.sql
    文件夹2_002
        table.sql
```
## 关键字
```
EXEC 默认执行校验
TABLE 修改表信息
```

## 校验语法
### 语法约定
```
-- 关键字.[次关键字].[校验节点].[执行节点](校验SQL/关键字);
```
### 通用校验语句
```sql
/*在默认数据节点校验并执行*/
-- EXEC(校验sql);
/*在master数据节点校验并执行*/
-- EXEC.master(校验sql);
/*在master节点校验，在slave节点执行*/
-- EXEC.master.slave(校验sql);
```

### 修改表信息
```sql
/*在默认节点校验并执行*/
-- TABLE.CREATE(表名)
```
### 修改字段信息
```sql
-- CHANGE_ADD(表名.字段名);
-- CHANGE_DROP(表名.字段名);#暂不适配
-- CHANGE_NAME(表名.字段名);
-- CHANGE_TYPE(表名.字段名.修改后的类型);
-- CHANGE_LENGTH(表名.字段名.修改后的长度);
-- CHANGE_COMMENT(表名.字段名.修改后的备注);
```
