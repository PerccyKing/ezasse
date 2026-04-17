import clsx from 'clsx';
import Link from '@docusaurus/Link';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import Layout from '@theme/Layout';
import Heading from '@theme/Heading';
import {ezasseConfig} from "@site/config/ezasse.config";
import {Analytics} from "@vercel/analytics/react";
import {SpeedInsights} from '@vercel/speed-insights/react';
import styles from './index.module.css';
import {JSX} from 'react';
import Translate from "@docusaurus/Translate";

/* ───────────── Hero ───────────── */
function HomepageHeader() {
  const {siteConfig} = useDocusaurusContext();
  return (
    <header className={styles.heroBanner}>
      <div className={clsx('container', styles.heroInner)}>
        <div className={styles.heroText}>
          <div className={styles.heroBadge}>
            <span className={styles.heroBadgeDot}/>
            <Translate>自动化 · 无侵入 · 高扩展</Translate>
          </div>
          <Heading as="h1" className={styles.heroTitle}>
            <span className={styles.heroTitleBrand}>ezasse</span>
          </Heading>
          <p className={styles.heroSubtitle}>
            <Translate>Easy Automatic SQL Script Executor</Translate>
          </p>
          <p className={styles.heroDesc}>
            <Translate>
              通过声明式校验语法让 SQL 脚本自动识别是否需要执行，无需维护版本记录表，
              天然幂等，轻松融入任何 Spring Boot 项目。
            </Translate>
          </p>
          <div className={styles.heroCtas}>
            <Link className={clsx('button button--lg', styles.btnPrimary)} to="/docs/start/springboot2_mysql">
              <Translate>🚀 快速开始</Translate>
            </Link>
            <Link className={clsx('button button--lg', styles.btnOutline)} to="/docs">
              <Translate>📖 查看文档</Translate>
            </Link>
            <Link className={clsx('button button--lg', styles.btnGhost)} href={ezasseConfig.github} target="_blank">
              <Translate>⭐ GitHub</Translate>
            </Link>
          </div>
        </div>

        <div className={styles.heroCode}>
          <div className={styles.heroCodeTitle}>init.sql</div>
          <div className={styles.heroCodeBody}>
            <pre><code>
{`-- 表不存在时创建
-- TABLE(t_user)
CREATE TABLE t_user (
  id      BIGINT PRIMARY KEY,
  account VARCHAR(64) NULL,
  name    VARCHAR(64) NULL
);

-- 字段不存在时添加
-- ADD(t_user.email)
ALTER TABLE t_user
    ADD email VARCHAR(128) NULL;

-- SQL 返回 0 时插入数据
-- EXEC(SELECT COUNT(1) FROM t_user WHERE id = 1)
INSERT INTO t_user(id, name)
VALUES (1, 'admin');`}
            </code></pre>
          </div>
        </div>
      </div>
    </header>
  );
}

/* ───────────── 特性卡片 ───────────── */
type Feature = { icon: string; title: string; desc: string };
const features: Feature[] = [
  {icon: '💡', title: '声明式语法', desc: '用特殊注释标记 SQL 脚本，逐行扫描、按条件决定是否执行，无需维护额外的版本状态。'},
  {icon: '🔁', title: '天然幂等', desc: '每次启动重新校验，条件满足才执行。已应用的脚本永远不会二次执行，彻底消除重复执行风险。'},
  {icon: '🧩', title: '无侵入集成', desc: '一个注解 @EnableEzasse 即可完成 Spring Boot 接入，不影响现有任何架构与流程。'},
  {icon: '🔌', title: '高度可扩展', desc: '校验器、执行器、数据源、资源加载器均开放扩展点，支持 SPI 和 Spring Bean 两种注册方式。'},
  {icon: '🗄️', title: '多数据源', desc: '文件名与校验行均可指定数据源节点，校验与执行可在不同节点上独立运行，轻松应对复杂场景。'},
  {icon: '📂', title: '分组有序管理', desc: '文件命名约定支持分组（版本号）和顺序号，多版本迭代脚本一览无余，执行顺序可控。'},
];

function FeaturesSection() {
  return (
    <section className={styles.featuresSection}>
      <div className="container">
        <Heading as="h2" className={styles.sectionTitle}><Translate>核心特性</Translate></Heading>
        <div className={styles.featureGrid}>
          {features.map(f => (
            <div key={f.title} className={styles.featureCard}>
              <div className={styles.featureIcon}>{f.icon}</div>
              <Heading as="h3" className={styles.featureTitle}>{f.title}</Heading>
              <p className={styles.featureDesc}>{f.desc}</p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}

/* ───────────── 快速安装 ───────────── */
function QuickInstallSection() {
  return (
    <section className={styles.quickInstallSection}>
      <div className="container">
        <Heading as="h2" className={styles.sectionTitle}><Translate>三步快速接入</Translate></Heading>
        <div className={styles.stepsGrid}>
          <div className={styles.stepCard}>
            <div className={styles.stepNumber}>1</div>
            <Heading as="h3" className={styles.stepTitle}>添加依赖</Heading>
            <pre className={styles.stepCode}><code>{`<dependency>
  <groupId>cn.com.pism</groupId>
  <artifactId>
    ezasse-spring-boot-starter
  </artifactId>
  <version>latest</version>
</dependency>`}</code></pre>
          </div>
          <div className={styles.stepCard}>
            <div className={styles.stepNumber}>2</div>
            <Heading as="h3" className={styles.stepTitle}>启用注解</Heading>
            <pre className={styles.stepCode}><code>{`@SpringBootApplication
@EnableEzasse
public class Application {
  public static void main(
      String[] args) {
    SpringApplication.run(
      Application.class, args);
  }
}`}</code></pre>
          </div>
          <div className={styles.stepCard}>
            <div className={styles.stepNumber}>3</div>
            <Heading as="h3" className={styles.stepTitle}>编写 SQL</Heading>
            <pre className={styles.stepCode}><code>{`-- TABLE(t_user)
CREATE TABLE t_user (
  id   BIGINT PRIMARY KEY,
  name VARCHAR(64)
);

-- ADD(t_user.email)
ALTER TABLE t_user
  ADD email VARCHAR(128);`}</code></pre>
          </div>
        </div>
        <div className={styles.quickInstallCta}>
          <Link className={clsx('button button--lg', styles.btnPrimary)} to="/docs/start/springboot2_mysql">
            <Translate>查看完整接入指南 →</Translate>
          </Link>
        </div>
      </div>
    </section>
  );
}

/* ───────────── 模块架构 ───────────── */
const modules = [
  {name: 'ezasse-core', tag: '核心', desc: '抽象骨架、上下文管理、Manager 接口定义，无框架依赖'},
  {name: 'ezasse-for-jdbc', tag: 'JDBC', desc: '基于 Spring JdbcTemplate 的执行器与校验器实现'},
  {name: 'ezasse-for-jdbc-enhance', tag: 'JDBC+', desc: 'JDBC 增强实现，更多数据库特性支持'},
  {name: 'ezasse-for-nacos', tag: 'Nacos', desc: '从 Nacos 配置中心加载 SQL 脚本资源'},
  {name: 'ezasse-spring-boot-starter', tag: 'Starter', desc: 'Spring Boot 自动装配，一个注解即可接入'},
];

function ModulesSection() {
  return (
    <section className={styles.modulesSection}>
      <div className="container">
        <Heading as="h2" className={styles.sectionTitle}><Translate>模块说明</Translate></Heading>
        <div className={styles.moduleList}>
          {modules.map(m => (
            <div key={m.name} className={styles.moduleItem}>
              <div className={styles.moduleLeft}>
                <code className={styles.moduleName}>{m.name}</code>
                <span className={styles.moduleTag}>{m.tag}</span>
              </div>
              <p className={styles.moduleDesc}>{m.desc}</p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}

/* ───────────── 页脚 CTA ───────────── */
function FooterCTA() {
  return (
    <section className={styles.footerCta}>
      <div className="container">
        <Heading as="h2" className={styles.footerCtaTitle}>
          <Translate>开始你的第一个 ezasse 项目</Translate>
        </Heading>
        <p className={styles.footerCtaDesc}>
          <Translate>五分钟即可完成接入，让数据库脚本管理从此不再烦恼。</Translate>
        </p>
        <div className={styles.heroCtas}>
          <Link className={clsx('button button--lg', styles.btnPrimary)} to="/docs/start/springboot2_mysql">
            <Translate>🚀 立即开始</Translate>
          </Link>
          <Link className={clsx('button button--lg', styles.btnOutline)} href={ezasseConfig.github} target="_blank">
            <Translate>⭐ Star on GitHub</Translate>
          </Link>
        </div>
      </div>
    </section>
  );
}

/* ───────────── 组合 ───────────── */
export default function Home(): JSX.Element {
  const {siteConfig} = useDocusaurusContext();
  return (
    <Layout
      title={`${siteConfig.title} · 项目脚本管理方案`}
      description="ezasse — Easy Automatic SQL Script Executor：声明式、幂等、无侵入的自动 SQL 执行框架">
      <HomepageHeader/>
      <main>
        <FeaturesSection/>
        <QuickInstallSection/>
        <ModulesSection/>
        <FooterCTA/>
      </main>
      <Analytics/>
      <SpeedInsights/>
    </Layout>
  );
}
