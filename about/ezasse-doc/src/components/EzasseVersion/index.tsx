import {JSX} from "react";
import Admonition from '@theme/Admonition';
import {ezasseConfig} from "@site/config/ezasse.config";


export default function EzasseVersion(): JSX.Element {
  return (
    <>
      <Admonition type="info" title={"当前版本信息"}>
        <p>ezasse.version:
          <a href={ezasseConfig.github + '/tree/v' + ezasseConfig.currentVersion} target={"_blank"}>{ezasseConfig.currentVersion}</a>
        </p>
      </Admonition>
    </>
  );
}
