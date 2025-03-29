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
        <TabItem value="maven" label="maven">

          <CodeBlock
            language="xml"
            title="pom.xml"
            showLineNumbers>
            {`<!-- ezasse 核心-->
<dependency>
    <groupId>cn.com.pism</groupId>
    <artifactId>ezasse-core</artifactId>
    <version>${ezasseConfig.currentVersion}</version>
</dependency>

<!-- ezasse jdbc实现-->
<dependency>
    <groupId>cn.com.pism</groupId>
    <artifactId>ezasse-for-jdbc</artifactId>
    <version>${ezasseConfig.currentVersion}</version>
</dependency>

<!-- mysql 驱动-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.31</version>
</dependency>

<!-- spring jdbc 支持-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.39</version>
</dependency>`}
          </CodeBlock>
        </TabItem>
        <TabItem value="gradle" label="gradle">
          <CodeBlock
            language="gradle"
            title="build.gradle"
            showLineNumbers>
            {``}
          </CodeBlock>
        </TabItem>
      </Tabs>
    </>
  );
}