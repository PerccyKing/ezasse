import {themes as prismThemes} from 'prism-react-renderer';
import type {Config} from '@docusaurus/types';
import type * as Preset from '@docusaurus/preset-classic';
import {ezasseConfig} from "./config/ezasse.config";
import {navbarArr} from "./config/navbar.config";
import remarkMath from 'remark-math';
import rehypeKatex from 'rehype-katex';
// This runs in Node.js - Don't use client-side code here (browser APIs, JSX...)

const config: Config = {
  themes: ['@docusaurus/theme-mermaid'],
  markdown: {
    mermaid: true,
  },
  title: 'ezasse',
  tagline: '项目脚本管理方案',
  favicon: 'img/favicon.ico',

  // Set the production url of your site here
  url: process.env.DEPLOY_URL || 'https://ezassse.pism.com.cn',
  // Set the /<baseUrl>/ pathname under which your site is served
  // For GitHub pages deployment, it is often '/<projectName>/'
  baseUrl: process.env.BASE_URL || '/',

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
            'https://github.com/PerccyKing/ezasse/about/ezasse-doc/',
          lastVersion: 'current',
          remarkPlugins: [remarkMath],
          rehypePlugins: [rehypeKatex],

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
            'https://github.com/PerccyKing/ezasse/about/ezasse-doc/blog/',
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
    image: 'img/ezasse-social-card.png',
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
        ...navbarArr,
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

    stylesheets: [
      {
        href: 'https://cdn.jsdelivr.net/npm/katex@0.13.24/dist/katex.min.css',
        type: 'text/css',
        integrity:
          'sha384-odtC+0UGzzFL/6PNoE8rX/SPcQDXBJ+uRepguP4QkPCm2LBxH3FA3y+fKSiJ+AmM',
        crossorigin: 'anonymous',
      },
    ],
  } satisfies Preset.ThemeConfig,

  plugins: [
    [
      '@docusaurus/plugin-ideal-image',
      {
        quality: 70,
        max: 1030, // max resized image's size.
        min: 640, // min resized image's size. if original is lower, use that size.
        steps: 2, // the max number of images generated between min and max (inclusive)
        disableInDev: false,
      },
    ],
  ],
};

export default config;
