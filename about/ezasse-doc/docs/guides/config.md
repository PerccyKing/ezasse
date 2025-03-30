# 配置

## 配置项

### 基本配置

| 名称   | springboot配置                | 类型       | 是否必填    | 默认值       | 示例值                            | 描述                                           |
|------|-----------------------------|----------|---------|-----------|--------------------------------|----------------------------------------------|
| 是否开启 | `spring.ezasse.enable`      | Boolean  | `true`  | `true`    | `true\|false`                  | 是否启用ezasse,如果关闭，ezasse不会实例化                  |
| 是否执行 | `spring.ezasse.execute`     | Boolean  | `true`  | `true`    | `true\|false`                  | 是否执行ezasse，如果关闭，项目启动后不会自动执行                  |
| 文件夹  | `spring.ezasse.folders`     | String[] | `true`  | `['sql']` | `['sql','doc1']`               | 会扫描当前文件夹下所有的sql文件,文件夹最好有三位顺序标记，没有标记将按照默认排序执行 |
| 文件列表 | `spring.ezasse.file-list`   | String[] | `false` |           | `['sql/v0.0.1-100-table.sql']` | 未指定，将校验全部的sql文件，可以是sql文件的完整名称，也可以是文件的分组名称    |
| 分组顺序 | `spring.ezasse.group-order` | String[] | `false` |           | `['v0.0.1']`                   | 如果存在多个分组，需要指定分组执行顺序                          |

### 限定符配置

:::tip

成对使用，校验行以后的内容，只有在限定符内，才会被执行
eg:

```sql
-- TABLE(user)
-- [
CREATE TABLE `user`
(
    id   int          not null auto_increment,
    name varchar(255) not null
)
-- ]

```

:::

| 名称    | springboot配置                    | 类型     | 是否必填    | 默认值 | 示例值    | 描述    |
|-------|---------------------------------|--------|---------|-----|--------|-------|
| 开始限定符 | `spring.ezasse.delimiter-start` | String | `false` |     | `-- [` | 开始限定符 |
| 结束限定符 | `spring.ezasse.delimiter-end`   | String | `false` |     | `-- ]` | 结束限定符 |

### 关键字配置

:::tip

如果原有关键字不适合，可以自定义关键字

:::

| 名称       | springboot配置                                   | 类型     | 是否必填    | 默认值              | 示例值              | 描述 |
|----------|------------------------------------------------|--------|---------|------------------|------------------|----|
| 通用校验关键字  | `spring.ezasse.key-words.exec`                 | String | `false` | `EXEC`           | `EXEC`           |    |
| 创建表关键字   | `spring.ezasse.key-words.table.create-table`   | String | `false` | `TABLE`          | `TABLE`          |    |
| 添加字段     | `spring.ezasse.key-words.field.add`            | String | `false` | `ADD`            | `ADD`            |    |
| 修改字段名称   | `spring.ezasse.key-words.field.change-name`    | String | `false` | `CHANGE_NAME`    | `CHANGE_NAME`    |    |
| 修改字段数据长度 | `spring.ezasse.key-words.field.change-length`  | String | `false` | `CHANGE_LENGTH`  | `CHANGE_LENGTH`  |    |
| 修改字段类型   | `spring.ezasse.key-words.field.change-type`    | String | `false` | `CHANGE_TYPE`    | `CHANGE_TYPE`    |    |
| 修改字段备注   | `spring.ezasse.key-words.field.change-comment` | String | `false` | `CHANGE_COMMENT` | `CHANGE_COMMENT` |    |

## SpringBoot

:::tip
启动类一定要添加 `@EnableEzasse` 注解
:::

```yaml title="application.yml" showLineNumbers
spring:
  ezasse:
    enable: true
    execute: true
    folders:
      - sql
      - sql1
      - sql2
    file-list:
      - sql/v0.0.1-100-table.sql
      - sql/v0.0.1-200-data.sql
      - sql1/sql1.sql
    group-order:
      - v0.0.1
    delimiter-start: -- [
    delimiter-end: -- ]
    key-words:
      exec: EXEC
      table:
        create-table: TABLE
      field:
        add: ADD
        change-name: CHANGE_NAME
        change-length: CHANGE_LENGTH
        change-type: CHANGE_TYPE
        change-comment: CHANGE_COMMENT
```