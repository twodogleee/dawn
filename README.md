# Dawn

<p align="center">
	<strong>dawn取自黎明破晓之意</strong>
</p>
<p align="center">
	<a href="http://www.54year.com"><img src="http://54year.com/usr/uploads/2020/08/650730182.png" height="300px" width="300px"></a>
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



#### 更新说明

##### 2020-09-07 
```
1.新增MybatisPlus分页插件配置
2.新增角色管理相关功能
```

##### 2020-09-04 
```
1.完成了网关通过查询数据库中的url角色信息来进行统一的鉴权
2.提供了HasRole注解对接口进行单独鉴权
3.增加了token增强

后续计划url与角色关联信息放到redis
然后需要增加同一client_id重复登录时使上一个token失效来解决Jwttoken下发后无法主动失效问题

```




