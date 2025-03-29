import {JSX} from "react";
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import CodeBlock from '@theme/CodeBlock';
import {ezasseConfig} from "@site/config/ezasse.config";
import EzasseVersion from "@site/src/components/EzasseVersion";

export default function EzasseSpringbootStarterDependency({showVersion = false}: {
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
            {`<dependency>
    <groupId>cn.com.pism</groupId>
    <artifactId>ezasse-spring-boot-starter</artifactId>
    <version>${ezasseConfig.currentVersion}</version>
</dependency>`}
          </CodeBlock>
        </TabItem>
        <TabItem value="gradle" label="gradle">
          <CodeBlock
            language="gradle"
            title="build.gradle"
            showLineNumbers>
            {`implementation group: 'cn.com.pism', name: 'ezasse-spring-boot-starter', version: '${ezasseConfig.currentVersion}'`}
          </CodeBlock>
        </TabItem>
      </Tabs>
    </>
  );
}

