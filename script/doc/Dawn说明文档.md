# Dawn说明文档

|文档版本|修订说明|修订人|修订时间|
|-|-|-|-|
|1.0.0|项目相关说明|Andersen|2022-04-02|

[toc]


## 1.导入项目
使用GitBashHere执行命令将代码克隆到本地
```
git clone https://github.com/suucx/dawn.git
```
使用Idea等工具导入项目即可
> idee使用RunDashboard批量启动微服务
> 在`.idea/workspace.xml`中新增如下内容;如果存在RunDashboard标签则在标签中添加option的内容即可,添加后随便启动一个服务则会出现services的table栏
> ```
> <component name="RunDashboard">
>    <option name="configurationTypes">
>      <set>
>        <option value="SpringBootApplicationConfigurationType" />
>      </set>
>    </option>
>  </component> 
>  ```




## 2.相关配置及数据库
1. 将项目中`script下dawn-auth.yaml,dawn_basic.yaml,gateway_route.js`配置与nacos中,group为dawn
> 其中数据库相关配置修改为实际配置信息

![](https://github.com/suucx/dawn/blob/master/script/doc/imgs/1.png)
2. 修改各个服务下`bootstrap.yml`文件中的`spring:cloud:nacos:config:server-addr`,`spring:cloud:nacos:discovery:server-addr`为实际的nacos地址
3. 将项目中`script下dawn.sql`导入数据库,库名为dawn编码为utf8mb4

## 3.其他说明

**启动项目:auth,admin,greatway**

### 3.1. 获取token
经网关调用接口`http://localhost:8080/dawn-auth/oauth/token`
username:admin 密码:123456
![](https://github.com/suucx/dawn/blob/master/script/doc/imgs/2.png)
### 3.2. Oauth2.0 授权码认证方式
```
Grant Type:code //授权码模式
Callback URL:https://www.54year.com //授权码回调地址
Auth URL:http://localhost:8080/dawn-auth/oauth/authorize //获取授权码地址
Access Token URL:http://localhost:8080/dawn-auth/oauth/token //授权码换取token地址
Client ID:test //客户端id 需要支持authorization_code 授权模式
Client Secret:test //客户端secret
Scope:all //访问域
State:getTokenTest //其他参数回调时会原本返回
2.1 请求授权地址获取授权码
PATH:http://192.168.1.119:8080/dawn-auth/oauth/authorize?response_type=code&state=getTokenTest&client_id=test&scope=all&redirect_uri=https%3A%2F%2Fwww.54year.com
2.2 获取到授权码后使用授权码换取token
PATH:http://localhost:8080/dawn-auth/oauth/token
questParam:
grant_type: "authorization_code" //获取token模式
code: "nNP34W" //授权码 callBack地址上的code
redirect_uri: "https://www.54year.com" //重定向url
client_id: "test" //客户端id
client_secret: "test" //客户端Secret
请求后即可使用授权码换取token
```
### 3.3. 服务列表端口

| 服务名       | 服务端口 | 服务说明        |
| ------------ | -------- | --------------- |
| dawn-admin   | 8082     | 后管服务        |
| dawn-auth    | 8081     | token授权及认证 |
| dawn-gateway | 8080     | 网关服务        |
| dawn-excel   | 8083     | excel公共服务   |

