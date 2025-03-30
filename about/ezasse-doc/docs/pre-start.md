# 开始之前

在开始使用ezasse之前需要对ezasse进行简单了解

## 原理

Ezasse 通过**资源加载器**对所有文件进行扫描，在获取到文件之后，利用**资源解析器**将其解析为**资源对象**
。接着，它会遍历这些资源文件，逐行实施校验操作。在校验过程中，依据校验行来获取对应的**校验器**
，进而对校验行中的校验内容进行校验。一旦校验通过，相关内容就会被传递给**执行器**，由**执行器**执行相应的操作。

### 示意图

![process.svg](../static/img/process.svg)

### 时序图

```mermaid
sequenceDiagram
    participant RF as 资源文件
    participant RL as 资源加载器
    participant RP as 资源解析器
    participant V as 校验器
    participant E as 执行器
    participant DS as 数据源
    RL ->> RF: 加载资源文件到内存
    RL ->> RP: 递交加载的资源
    RP ->> RP: 解析资源文件为校验行执行内容对象
    loop 遍历校验行执行内容对象列表
        RP ->> RP: 解析校验行获取校验关键字、执行数据源节点、校验数据源节点与校验内容
        RP ->> V: 通过校验关键字获取校验器并递交校验内容
        V ->> DS: 通过数据源节点获取执行器
        V ->> E: 调用执行器动作进行校验
        alt 校验通过
            V ->> DS: 通过执行数据源获取执行器
            V ->> E: 递交执行内容
            E ->> E: 执行内容
        end
    end
```

## 术语

### 校验行

校验行 主要由 校验关键字 和 校验语句 组成，并且可以指定校验节点和执行节点

校验节点和数据节点分别为数据节点

#### 语法

:::tip 语法
`-- 关键字[.校验节点][.执行节点](校验内容)`
:::

#### 示例

```SQL
-- 当数据库中不存在表user，执行执行内容
-- TABLE(user)
```

### 执行内容

执行内容也是***可选的***，执行内容的范围为校验行到校验行直接的内容，如果指定了开始限定符和结束限定符，执行内容就只包含限定符区间的内容

#### 语法

:::tip 语法
> 开启限定符配置 `-- [`,`-- ]`

```SQL showLineNumbers
-- TABLE(user)
-- [
CREATE TABLE user
(
    id   BIGINT NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    name VARCHAR(1024) NULL COMMENT '账号'
) COMMENT '用户表';
-- ]
```

:::