# Dawn开发文档

### 1.导入项目
使用GitBashHere执行命令将代码克隆到本地
```
git clone https://github.com/suucx/dawn.git
```
使用Idea等工具导入项目即可
> idee使用RunDashboard批量启动微服务
> 在`.idea/workspace.xml`中新增如下内容;如果存在RunDashboard标签则在标签中添加option的内容即可,添加后随便启动一个服务则会出现services的table栏
>> ```
>> <component name="RunDashboard">
>>    <option name="configurationTypes">
>>      <set>
>>        <option value="SpringBootApplicationConfigurationType" />
>>      </set>
>>    </option>
>>  </component> 
>>  ```




### 2.相关配置及数据库
1. 将项目中`script下dawn-auth.yaml,dawn_basic.yaml,gateway_route.js`配置与nacos中,group为dawn
> 其中数据库相关配置修改为实际配置信息

![](https://github.com/suucx/dawn/blob/master/script/doc/imgs/1.png)
2. 修改各个服务下`bootstrap.yml`文件中的`spring:cloud:nacos:config:server-addr`,`spring:cloud:nacos:discovery:server-addr`为实际的nacos地址
3. 将项目中`script下dawn.sql`导入数据库,库名为dawn编码为utf8mb4

### 3.其他说明

**启动项目:auth,admin,greatway**

1. 获取token
经网关调用接口`http://localhost:8080/dawn-auth/oauth/token`
username:admin 密码:123456
![](https://github.com/suucx/dawn/blob/master/script/doc/imgs/2.png)
