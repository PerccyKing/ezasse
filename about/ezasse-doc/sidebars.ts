import type {SidebarsConfig} from '@docusaurus/plugin-content-docs';

// This runs in Node.js - Don't use client-side code here (browser APIs, JSX...)

/**
 * Creating a sidebar enables you to:
 - create an ordered group of docs
 - render a sidebar for each doc of that group
 - provide next/previous navigation

 The sidebars can be generated from the filesystem, or explicitly defined here.

 Create as many sidebars as you want.
 */
const sidebars: SidebarsConfig = {
  docs: [
    'index',
    'pre-start',
    {
      type: 'category',
      label: '快速开始',
      link: {
        type: 'generated-index',
      },
      collapsed: false,
      items: [
        'start/springboot2_mysql'
      ],
    },
    {
      type: 'category',
      label: '指南',
      link: {
        type: 'generated-index',
      },
      collapsed: false,
      items: [
        'guides/install',
        'guides/config',
        'guides/file_naming_rules',
        'guides/grammar',
      ],
    },
    {
      type: 'category',
      label: '进阶',
      link: {
        type: 'generated-index',
      },
      collapsed: false,
      items: [
        'advanced/checker',
        'advanced/executor',
        'advanced/datasource',
      ],
    },

  ],

};

export default sidebars;