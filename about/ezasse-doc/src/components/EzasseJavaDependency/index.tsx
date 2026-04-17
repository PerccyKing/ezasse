import {JSX} from "react";
import EzasseVersion from "@site/src/components/EzasseVersion";
import TabItem from "@theme/TabItem";
import CodeBlock from "@theme/CodeBlock";
import {ezasseConfig} from "@site/config/ezasse.config";
import Tabs from "@theme/Tabs";

export default function EzasseJavaDependency({showVersion = false}: {
  showVersion?: boolean
}): JSX.Element {
  return (
    <>
      {showVersion && <EzasseVersion/>}
      <Tabs groupId="versionManager">
        <TabItem value="maven" label="Maven">
          <CodeBlock
            language="xml"
            title="pom.xml"
            showLineNumbers>
            {`<!-- ezasse 核心 -->
<dependency>
    <groupId>cn.com.pism</groupId>
    <artifactId>ezasse-core</artifactId>
    <version>${ezasseConfig.currentVersion}</version>
</dependency>

<!-- ezasse jdbc 实现 -->
<dependency>
    <groupId>cn.com.pism</groupId>
    <artifactId>ezasse-for-jdbc</artifactId>
    <version>${ezasseConfig.currentVersion}</version>
</dependency>

<!-- MySQL 驱动 -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.3.0</version>
</dependency>

<!-- spring-jdbc 支持 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.39</version>
</dependency>`}
          </CodeBlock>
        </TabItem>
        <TabItem value="gradle" label="Gradle">
          <CodeBlock
            language="groovy"
            title="build.gradle"
            showLineNumbers>
            {`// ezasse 核心
implementation 'cn.com.pism:ezasse-core:${ezasseConfig.currentVersion}'

// ezasse jdbc 实现
implementation 'cn.com.pism:ezasse-for-jdbc:${ezasseConfig.currentVersion}'

// MySQL 驱动
runtimeOnly 'com.mysql:mysql-connector-j:8.3.0'

// spring-jdbc 支持
implementation 'org.springframework:spring-jdbc:5.3.39'`}
          </CodeBlock>
        </TabItem>
      </Tabs>
    </>
  );
}