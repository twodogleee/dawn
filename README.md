# Dawn
dawn取自黎明破晓之意
#### 简介：
**dawn为一个springCloud微服务框架，于个人学习使用！！！**

- 基于SpringBoot/SpringCloud；
- 使用nacos作为配置与服务中心；
- 使用springCloud-gateway中间件作为网关；

#### 版本及环境信息：
- idea 
- JDK8 
- Springboot-2.3.0.RELEASE 
- SpringCloud-Hoxton.SR4
- Nacos 1.2.1

#### 服务模块：

- dawn-auth：认证模块
- dawn-common:基础模块
- dawn-gateway：网关模块

#### 功能清单：
|所属模块|功能|情况|
|-|-|-|
|dawn-gateway|动态路由|√|
|dawn-common|jwt工具|√|
|dawn-gateway|jwt验签|×|
|dawn-gateway|Hystrix熔断|×|



