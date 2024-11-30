export type EzasseConfig = {
  //主站地址
  mainHref?: string,
  //0.x 版本地址
  v0xHref: string,
  //简短的站点描述
  shortDes: string,
  github?: string,
  gitee?: string,
  pismOssGithub?: string
}


export const ezasseConfig: EzasseConfig = {
  mainHref: 'https://pism.com.cn',
  v0xHref: 'https://v0x.ezasse.pism.com.cn',
  shortDes: '项目SQL脚本管理方案',
  github: 'https://github.com/PerccyKing/ezasse',
  gitee: 'https://gitee.com/perccyking/ezasse',
  pismOssGithub: 'https://github.com/pism-oss'
}