---
description: 项目脚本管理方案
---

# 简介

ezasse **/ˈiːzi æs/**  是一个项目脚本管理方案

## 特性

* **简单**：按照开发流程编写脚本，添加校验标记就可以进行自动变更
* **自动化**：通过脚本规则自动执行变更，无需手动操作
* **无侵入**：不影响现有项目架构，任意插拔，只关注脚本或配置变更
* **高扩展**：预留多个扩展点，全流程可自定义

## 背景

在项目开发过程中，数据库脚本经常发生变更，通常采用文件记录的方式进行管理。然而，在测试、预发布及正式发布阶段，测试人员与运维人员需手动执行这些脚本，但由于文件传输或权限问题，可能导致执行失败或遗漏，从而影响系统的正常发布。

为了解决这一问题，我们希望在应用运行时自动执行数据库脚本，确保脚本与项目版本同步管理，从而降低人为操作失误的风险，提高发布流程的稳定性和可靠性。

:::tip[愿景]

所有项目的依赖中间件配置，都可以通过ezasse自动变更，减少人为操作失误

:::

## 关于名称

关于命名主要是为了sql脚本的版本管理，所以取名为 ***Easy***<sup>ez</sup> ***automatic***<sup>a</sup> ***SQL***<sup>s</sup>
***script***<sup>s</sup> ***executor***<sup>e</sup>，简写为 **ezasse**

## 架构

![ezasse-diagram.svg](../static/img/ezasse-diagram.svg)

## 代码托管

* [github](https://github.com/PerccyKing/ezasse)
* [gitee](https://gitee.com/perccyking/ezasse)

## 参与贡献

欢迎所以开发者提交issue、pr

个人渺小 一起成长