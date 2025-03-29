import {ezasseConfig} from "./ezasse.config";
import {NavbarItem} from "@docusaurus/theme-common";

export const navbarArr: NavbarItem[] = [
  {
    type: 'docSidebar',
    position: 'left',
    sidebarId: 'docs',
    label: '文档',
  },
  {
    position: 'left',
    label: '0.x文档',
    href: ezasseConfig.v0xHref
  },
  {to: '/blog', label: '博客', position: 'left'},
  {
    href: ezasseConfig.github,
    label: 'GitHub',
    position: 'right',
  },
  {
    href: ezasseConfig.gitee,
    label: 'Gitee',
    position: 'right',
  },
]