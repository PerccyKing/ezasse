---
id: design
title: 设计思想与架构
sidebar_label: 设计思想
---

# 核心设计思想与架构解析

`ezasse` 的核心目标是提供一个简单、灵活且可扩展的自动化 SQL 脚本执行引擎。与其它的数据库版本控制工具（如 Flyway、Liquibase）依靠专门的记录表和严格的版本号管理不同，`ezasse` 采用**声明式脚本**与**条件检查**的方式来决定 SQL 是否需要执行，使得脚本本身更具自解释性和独立性。

下面我们从设计模式、核心流程和架构分层等视角，来解析 `ezasse` 的内部设计原理。

---

## 1. 核心流程与模板方法模式 (Template Method)

在 `ezasse-core` 的 `AbstractEzasse` 中，使用了**模板方法模式**定义了整个系统运转的生命周期骨架。

核心逻辑被抽象为 `execute()` 方法，分为两步：
1. **加载与解析 (`loadAndParse()`)**：包括前置处理（`preProcess()`），使用资源加载器加载元数据（`loadEzasseResource()`），并使用解析器转换为标准结构数据（`resolveToResourceData()`），最后放入上下文中缓存。
2. **执行处理 (`doExecute()`)**：此方法作为抽象方法，由具体的子类（如 `FileEzasse`）去实现不同的执行逻辑。

以最常见的 `FileEzasse` 为例，它的 `doExecute()` 流程会遍历解析出的所有可执行资源并依据规则检查，最后交由执行器去处理。

## 2. 上下文与管理器 (Context & Manager) 层

在 `ezasse` 中，引入了 `EzasseContext` 这一核心概念：
* **Context (上下文)**：贯穿生命周期，内部包含了资源加载、解析、检查、执行器等的缓存与注册表。这也是一种上下文模式的体现。
* **Managers (管理器)**：系统中定义了一系列的管理器，比如 `ResourceLoaderManager`、`ResourceParserManager`、`CheckerManager`、`DatasourceManager`、`ExecutorManager`。

这些管理器承担了“调度员”的职责。引擎并不是将具体的类硬编码，而是通过管理器去查找相应的接口实现。

## 3. 策略模式与插件化机制 (Plugin & Strategy)

为了实现极高的扩展性，`ezasse` 将所有的多变行为抽象为接口，借由 Manager 在运行时动态选定。这一设计完全践行了**策略模式**和**开闭原则 (OCP)**。

主要扩展点包括：
* **资源加载 (`EzasseResourceLoader`)**：负责从哪里获取源数据，比如本地文件、Classpath、或者是 Nacos 配置中心 (`ezasse-for-nacos`模块)。
* **资源解析 (`EzasseResourceParser`)**：不同来源的数据可能是文本也可能是其他结构，解析器负责将源数据拆解成带有检查条件和执行脚本的标准模型 (`EzasseCheckLineContent`)。
* **条件检查器 (`EzasseChecker`)**：处理像 `-- TABLE(user)`、`-- EXEC(xxx)` 这样的拦截语句。无论是校验表是否存在还是校验字段，均可通过实现 `EzasseChecker` 接口来扩展出无限的基础语法。
* **执行器 (`EzasseExecutor`)**：最终与数据库直接打交道的角色。它与具体的底层驱动解耦（比如 `ezasse-for-jdbc` 中提供了基于 JDBC 的执行器）。只需根据数据源的类型获取匹配的 `EzasseExecutor` 即可。

## 4. 模块化与解耦设计 (Decoupling)

从项目结构可以清晰看出 `ezasse` 使用了极简的依赖机制：
- **ezasse-core**：核心骨架，包含抽象、模型、管理器及接口定义，**完全不依赖 Spring 也不依赖具体的 JDBC API**。
- **ezasse-for-jdbc / ezasse-for-jdbc-enhance**：提供针对传统关系型数据库（JDBC）的具体实现，实现了具体的 Checker、Executor 及 Datasource。
- **ezasse-for-nacos**：展示了系统可以很平滑地扩展对外部注册中心/配置中心的支持。
- **ezasse-spring-boot-starter**：基于 Spring Boot 的 AutoConfiguration 机制，将前面解耦好的各类组件（Loader, Checker, Executor, Context）在 Spring 容器启动时进行自动装配并触发执行操作，对开发者实现“零配置”。

## 5. 声明式的脚本设计思想

`ezasse` 返回到 SQL 脚本的初心，通过特殊的注释行（如 `-- TABLE(user)`）来给脚本注入判断逻辑。

**设计初衷**：
* 降低传统版本化管理工具带来的迁移或合并冲突痛点。
* 不需要数据库中额外维护由于迁移失败导致的脏锁或版本状态。
* 只要目标节点尚未处于声明的最终状态，即可触发执行。执行的幂等性很大程度上由判断语法（Checker）与 SQL 本身来保障。

## 总结

`ezasse` 之所以轻量且具扩展性，归功于其优雅的内核设计：**模板支撑基础流程，Context 解决状态扭转，一系列 Manager 和 策略接口提供无限挂载能力，结合声明式的 SQL 检查思想**，造就了这款独特的自动 SQL 执行器框架。
