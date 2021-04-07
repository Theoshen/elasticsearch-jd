# elasticSearch-jd

## 简介

SpringBoot 整合 elasticSearch，前后端分离，爬取京东搜索数据，展示到仿京东搜索页面的elasticSearch练手项目

## 环境依赖

- SrpingBoot 2.2.4
- ElastSearch 7.6.1
- Jsoup 1.13.1
- Vue 2.0

## 目录结构

### es-api

test 下有 SpringBoot 整合ElasticSearch 7.6.x 版本的基本 api 调用示例

### es-jd

- config  --> ElasticSearch client 配置
- controller   --> 控制层
- entity   --> Content  爬取数据实体类
- service  -->
- utils  --> jsoup 解析 html 工具类

## 部署安装

本机要有 elastisearch 7.6.1或者以上版本并启动

SpringBoot 默认端口为9090，启动并访问  localhost:9090 即可

## 版本内容更新

### v 1.0

1. 通过 localhost:9090/parse/{keyword}先爬取数据写入 es
2. localhost:9090 主页输入框搜索对应 keyword 查询