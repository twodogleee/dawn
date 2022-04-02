# Dawn

<p align="center">
	<strong>dawn取自黎明破晓之意</strong>
</p>
<p align="center">
	<a href="http://www.54year.com"><img src="https://www.54year.com/usr/uploads/2021/06/1117470442.png" height="300px" width="300px"></a>
</p>
<p align="center">
    <a href="https://www.oracle.com/technetwork/java/javase/downloads/index.html" rel="nofollow">
        <img src="https://img.shields.io/badge/JDK-1.8-blue" style="max-width:100%;">
    </a>
    <a href="https://spring.io/projects/spring-boot" rel="nofollow">
        <img src="https://img.shields.io/badge/SpringBoot-2.3.0.RELEASE-green" style="max-width:100%;">
    </a>
    <a href="https://spring.io/projects/spring-cloud" rel="nofollow">
        <img src="https://img.shields.io/badge/SpringCloud-Hoxton.SR4-green" style="max-width:100%;">
    </a>
    <a href="https://nacos.io/zh-cn/" rel="nofollow">
        <img src="https://img.shields.io/badge/nacos-1.2.1-blue" style="max-width:100%;">
    </a>
</p>



#### 简介：
**dawn为一个springCloud微服务框架，从零开始搭建；于个人学习使用！！！**


#### 版本及环境信息：
- idea 
- JDK8 
- Springboot-2.3.0.RELEASE 
- SpringCloud-Hoxton.SR4
- Nacos 1.2.1
- MySql 5.7

#### 服务模块：
```
├ dawn
├── dawn-core:基础类/常量等
├── dawn-common:通用功能模块
├── dawn-auth:认证中心
├── dawn-gateway：网关
├── dawn-jwt-spring-boot-stater:jwt工具
├── dawn-mysql:引入druid/MybatisPlus
├── dawn-generator:mybatisPlus代码生成模板
├── dawn-redis-spring-boot-starter:redis配置及操作
└──
```
#### 功能概要
```
1.使用SpringOAuth2进行授权,token采用jwt与RSA加密
2.网关统一进行验签与Url鉴权,提供注解进行单独的接口权限验证
3.网关配合Nacos实现动态路由
4.服务端直接调用使用fegin
```
#### 简易架构图：
![](https://github.com/suucx/dawn/blob/master/script/1.png)


#### [项目说明文档](https://github.com/suucx/dawn/blob/master/script/doc/dawn-doc.md)






