import {themes, themes as prismThemes} from 'prism-react-renderer';
import type {Config} from '@docusaurus/types';
import type * as Preset from '@docusaurus/preset-classic';
import {ezasseConfig} from "./ezasse.config";
// This runs in Node.js - Don't use client-side code here (browser APIs, JSX...)

const config: Config = {
  title: 'ezasse',
  tagline: '项目脚本管理方案',
  favicon: 'img/favicon.ico',

  // Set the production url of your site here
  url: 'https://ezassse.pism.com.cn',
  // Set the /<baseUrl>/ pathname under which your site is served
  // For GitHub pages deployment, it is often '/<projectName>/'
  baseUrl: '/',

  // GitHub pages deployment config.
  // If you aren't using GitHub pages, you don't need these.
  organizationName: 'PISM', // Usually your GitHub org/user name.
  projectName: 'ezasse-doc', // Usually your repo name.

  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',

  // Even if you don't use internationalization, you can use this field to set
  // useful metadata like html lang. For example, if your site is Chinese, you
  // may want to replace "en" with "zh-Hans".
  i18n: {
    defaultLocale: 'zh',
    locales: ['zh'],
  },

  presets: [
    [
      'classic',
      {
        docs: {
          sidebarPath: './sidebars.ts',
          // Please change this to your repo.
          // Remove this to remove the "edit this page" links.
          editUrl:
            'https://github.com/facebook/docusaurus/tree/main/packages/create-docusaurus/templates/shared/',
          lastVersion: 'current',
          versions: {
            current: {
              label: '1.0.0',
              path: '1.0.0',
            },

          },
        },
        blog: {
          showReadingTime: true,
          feedOptions: {
            type: ['rss', 'atom'],
            xslt: true,
          },
          // Please change this to your repo.
          // Remove this to remove the "edit this page" links.
          editUrl:
            'https://github.com/facebook/docusaurus/tree/main/packages/create-docusaurus/templates/shared/',
          // Useful options to enforce blogging best practices
          onInlineTags: 'warn',
          onInlineAuthors: 'warn',
          onUntruncatedBlogPosts: 'warn',
        },
        theme: {
          customCss: './src/css/custom.css',
        },
      } satisfies Preset.Options,
    ],
  ],

  themeConfig: {
    // Replace with your project's social card
    image: 'img/ezasse-social-card.svg',
    colorMode: {
      respectPrefersColorScheme: true
    },
    announcementBar: {
      id: 'announcementBar-1',
      content: 'ezasse 1.x 版本即将发布',
      backgroundColor: '#730a0a',
      textColor: '#ffffff',
      isCloseable: true,
    },
    navbar: {
      logo: {
        alt: 'ezasse Logo',
        src: 'img/logo.svg',
        srcDark: 'img/logo-dark.svg'
      },
      items: [
        {
          type: 'docSidebar',
          sidebarId: 'tutorialSidebar',
          position: 'left',
          label: '文档',
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
      ],
    },
    footer: {
      style: 'light',
      links: [
        {
          title: '关于',
          items: [
            {
              label: 'PISM Open Source Software',
              href: ezasseConfig.pismOssGithub
            }
          ]
        }
      ],
      logo: {
        alt: 'PISM',
        src: 'img/pism/logo.svg',
        srcDark: 'img/pism/logo-dark.svg',
        href: ezasseConfig.mainHref,
        width: 120
      },
      copyright: `© ${new Date().getFullYear()} <a href="${ezasseConfig.mainHref}">pism.com.cn</a> All Rights Reserved.
        <br/>
        <a href="https://beian.miit.gov.cn/">蜀ICP备19017495号-1</a>
`,
    },
    prism: {
      theme: prismThemes.github,
      darkTheme: prismThemes.dracula,
    },
  } satisfies Preset.ThemeConfig,

};

export default config;
