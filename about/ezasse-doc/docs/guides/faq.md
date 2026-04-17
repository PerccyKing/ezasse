# 常见问题（FAQ）

## 基础使用

### Q: SQL 脚本执行后，下次启动会重复执行吗？

**不会**。ezasse 的核心机制是**条件检查**，每次启动都会先执行校验逻辑（如检查表是否存在、字段是否存在），只有当校验条件不满足时，才会执行对应的 SQL 脚本。

因此，一旦 SQL 被成功执行（例如表已创建），后续每次启动校验都会通过并跳过该脚本，实现天然的**幂等性**。

---

### Q: 如何临时禁用 ezasse，不让它在启动时执行？

通过配置项关闭执行开关，但保留 ezasse 的实例化（便于后续开启）：

```yaml title="application.yml"
spring:
  ezasse:
    execute: false  # 仅关闭执行，不影响 Bean 创建
```

如果需要完全不实例化 ezasse：

```yaml
spring:
  ezasse:
    enable: false   # 完全关闭
```

---

### Q: 启动类必须添加 `@EnableEzasse` 注解吗？

是的，在 Spring Boot 项目中，`@EnableEzasse` 注解是 ezasse 自动装配的触发开关，缺少该注解则 ezasse 不会被初始化。

```java
@SpringBootApplication
@EnableEzasse  // 必须添加
public class Application { ... }
```

---

## SQL 文件

### Q: SQL 文件放在哪里？支持哪些目录结构？

SQL 文件默认放置在 `resources/sql/` 目录下。可以通过配置项 `spring.ezasse.folders` 自定义多个扫描目录：

```yaml
spring:
  ezasse:
    folders:
      - sql
      - db/migration
      - config/scripts
```

ezasse 会递归扫描这些目录下的所有 `.sql` 文件。

---

### Q: 文件中没有校验行时，SQL 会被执行吗？

**不会**。ezasse 只执行有校验行（如 `-- TABLE(...)` 、`-- EXEC(...)`）守卫的 SQL 代码块。没有校验行的普通 SQL 内容会被忽略。

---

### Q: 多个 SQL 文件的执行顺序如何控制？

通过两种方式控制：

1. **使用文件命名中的顺序号**（推荐）：文件名格式 `group-[datasource]-[order]-[other].sql`，其中 `order` 为三位数字，数字小的先执行。

   ```
   v1.0.0-100-table.sql   → 先执行
   v1.0.0-200-data.sql    → 后执行
   ```

2. **配置 `group-order`**：当存在多个分组（版本）时，指定分组执行顺序：

   ```yaml
   spring:
     ezasse:
       group-order:
         - v1.0.0
         - v1.1.0
   ```

---

## 校验与执行

### Q: 如何让一个校验行对应多条 SQL 语句？

使用**限定符**（`-- [` 和 `-- ]`）将多条 SQL 包裹在同一个执行块中：

```yaml title="application.yml"
spring:
  ezasse:
    delimiter-start: "-- ["
    delimiter-end: "-- ]"
```

```sql title="init.sql"
-- TABLE(t_order)
-- [
CREATE TABLE t_order ( id BIGINT PRIMARY KEY );
CREATE INDEX idx_order_id ON t_order(id);
-- ]
```

---

### Q: 遇到 `No checker found for checkerId: XXX` 错误怎么办？

这表示 SQL 文件中使用了某个关键字，但系统未找到对应的校验器。可能原因：

1. **关键字拼写错误**：检查 SQL 文件中的关键字是否与配置项或自定义校验器的 `getId()` 完全一致（大小写敏感）。
2. **未引入对应模块**：内置校验器（如 `TABLE`、`ADD`、`EXEC`）由 `ezasse-for-jdbc` 提供，请确认依赖已正确引入。
3. **SPI 未生效**：自定义校验器若使用 SPI 注册，检查 `META-INF/services/cn.com.pism.ezasse.checker.EzasseChecker` 文件路径和内容是否正确。

---

### Q: 如何确认 ezasse 是否正常执行了某条 SQL？

开启 debug 日志即可看到完整的执行过程：

```yaml title="application.yml"
logging:
  level:
    cn.com.pism.ezasse: debug
```

日志会输出每条校验行的校验结果（`check passed: [true/false]`）以及最终执行的 SQL 内容。

---

## 多数据源

### Q: 多数据源时，校验和执行用的数据源可以不同吗？

可以。通过校验行语法中的双节点格式来指定：

```sql
-- 关键字.校验节点.执行节点(校验内容)

-- 在 slave 节点校验，在 master 节点执行
-- TABLE.slave.master(t_order)
CREATE TABLE t_order ( id BIGINT PRIMARY KEY );
```

---

### Q: 非 Spring Boot 的普通 Java 项目如何使用 ezasse？

使用 `ezasse-core` + 对应的实现包（如 `ezasse-for-jdbc`），通过编程方式手动构建上下文并执行：

```xml
<dependency>
    <groupId>cn.com.pism</groupId>
    <artifactId>ezasse-for-jdbc</artifactId>
    <version>${ezasse.version}</version>
</dependency>
```

```java
// 构建数据源
EzasseDataSource dataSource = JdbcEzasseDataSource.of("master", "MYSQL", yourDataSource);

// 创建并配置 Ezasse 实例
FileEzasse ezasse = new FileEzasse();
ezasse.getContext().datasourceManager().registerMasterDataSource(dataSource);
ezasse.getContext().configManager().setFolders(List.of("sql"));

// 执行
ezasse.execute();
```

---

### Q: 使用 Nacos 存储 SQL 脚本如何配置？

引入 `ezasse-for-nacos` 模块，并参考该模块的文档进行配置，ezasse 会通过扩展的资源加载器从 Nacos 拉取脚本内容，整体执行流程与本地文件模式完全一致。
