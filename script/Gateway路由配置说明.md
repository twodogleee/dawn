## 1.Predicates
**配置路由匹配请求的规则**
#### 1.1 Http
```
yml配置，多个参数用逗号隔开
- Path = /aa/**,/bb/**

json配置
{"name":"Path","args":{"pattern":"/aa/**","pattern1":"/bb/**"}}
```
#### 1.2 Cookie配置对
```
Cookie中值的匹配，第一个为key，第二个为value;下例匹配cookie设置chocolate:ch.p的请求
yml配置
- Cookie = chocolate,ch.p

json配置
{"name":"Cookie","args":{"_genkey_0":"chocolate","_genkey_1":"ch.p"}}
```
#### 1.3 Header匹配
```
Http请求中设置的内容，http-header设置X-Request-Id:\d+可以匹配，第二个参数第二个参数是正则表达式
yml配置
- Header = X-Request-Id,\d+
 
json配置
{"name":"Header","args":{"_genkey_0":"X-Request-Id","_genkey_1":"\d+"}}
```
#### 1.4 Host匹配
```
Http请求Host，匹配所有host为**.somehost.com的请求
yml配置
- Host = **.somehost.com
 
json配置
{"name":"Host","args":{"_genkey_0":"**.somehost.com"}}
```
#### 1.5 Method匹配Http请求方式
```
yml配置
- Method = GET

json配置
{"name":"Method","args":{"_genkey_0":"GET"}}
```
#### 1.6 Query匹配
```
Http请求中的查询参数，请求中携带param1=value的请求可以匹配
yml配置
- Query = param1,value
 
json配置
{"name":"Query","args":{"_genkey_0":"param1","_genkey_1":"value"}}
```
#### 1.7 RemoteAddr匹配
```
请求中的RemoteAddr
yml配置
- RemoteAddr = 192.168.1.1/24
 
json配置
{"name":"RemoteAddr","args":{"_genkey_0":"192.168.1.1/24"}}
```
#### 1.8 时间相关
```
After设置时间之后可以访问
yml配置
- After = 2017-01-20T17:42:47.789-07:00[America/Denver]
 
json配置
{"name":"After","args":{"_genkey_0":"2017-01-20T17:42:47.789-07:00[America/Denver]"}}

Before设置时间之前可以访问
yml配置
- Before = 2017-01-20T17:42:47.789-07:00[America/Denver]
 
json配置
{"name":"Before","args":{"_genkey_0":"2017-01-20T17:42:47.789-07:00[America/Denver]"}}

Before设置时间段内可以访问
yml配置
- Between = 2017-01-20T17:42:47.789-07:00[America/Denver],2017-01-21T17:42:47.789-07:00[America/Denver]

json配置
{"name":"Between","args":{"_genkey_0":"2017-01-20T17:42:47.789-07:00[America/Denver]"，"_genkey_1":"2017-01-21T17:42:47.789-07:00[America/Denver]"}}
```
#### 1.9 权重路由
```
至少两组以上路由可以配置权重路由，配置后会根据权重随机访问几个路由
yml配置
- Weight = service1,80
 
json配置
{"name":"Weight","args":{"_genkey_0":"service1","_genkey_1":"80"}}
```

## 2.Filters
#### 2.1 路径重写
```
yml配置
- RewritePath = /path/(?<segment>.*), /$\{segment}
 
json配置
{"name":"RewritePath","args":{"_genkey_0":"/foo/(?<segment>.*)","_genkey_1":"/$\\{segment}"}}
```
#### 2.2 修改请求头
```
yml配置
- AddRequestHeader = X-Request-Foo,Bar
 
json配置
{"name":"AddRequestHeader","args":{"_genkey_0":"X-Request-Foo","_genkey_1":"Bar"}}
```
#### 2.3 修改请求参数
```
yml配置
- AddRequestParameter = foo,bar
 
json配置

{"name":"AddRequestParameter","args":{"_genkey_0":"foo","_genkey_1":"bar"}}
```
#### 2.4 修改响应参数
```
yml配置
- AddResponseHeader = X-Request-Foo,Bar
 
json配置
{"name":"AddResponseHeader","args":{"_genkey_0":"X-Request-Foo","_genkey_1":"Bar"}}
```
#### 2.5 路径前缀增强
```
请求路径/hello, 将会被替换为 /mypath/hello
yml配置
- PrefixPath = /mypath
 
json配置
{"name":"PrefixPath","args":{"_genkey_0":"/mypath"}}
```
#### 2.6 路径前缀删除
```
请求/name/bar/foo，去除掉前面两个前缀之后，最后转发到目标服务的路径为/foo
yml配置
- StripPrefix = 2
 
json配置
{"name":"StripPrefix","args":{"_genkey_0":"2"}}
```
#### 2.7 请求携带保留原始Host
```
yml配置
- PreserveHostHeader
 
json配置
{"name":"PreserveHostHeader","args":{}}
```
#### 2.8 重定向
```
yml配置
- RedirectTo = 302,http://acme.org
 
json配置
{"name":"RedirectTo","args":{"_genkey_0":"302","_genkey_1":"http://acme.org"}}
```
#### 2.9 断路器
```
yml配置
- name: Hystrix
  args:
      # 断路后跳转地址
      name: fallbackcmd
      fallbackUri: forward:/incaseoffailureusethis
 
json配置
{"name":"Hystrix","args":{"name":"fallbackcmd","fallbackUri":"forward:/incaseoffailureusethis"}}
```
#### 2.10 集成Redis原生支持请求限流
```
yml配置
 - name: RequestRateLimiter
   args:
     redis-rate-limiter.replenishRate: 10  
     redis-rate-limiter.burstCapacity: 20
 
json配置
{"name":"RequestRateLimiter","args":{"redis-rate-limiter.replenishRate":"10","redis-rate-limiter.burstCapacity":"20"}}
```
#### 2.11 删除请求头属性
```
yml配置
- RemoveRequestHeader = X-Request-Foo
 
json配置
{"name":"RemoveRequestHeader","args":{"_genkey_0":"X-Request-Foo"}}
```
#### 2.12 删除响应头属性
```
yml配置
- RemoveResponseHeader = X-Request-Foo
 
json配置
{"name":"RemoveResponseHeader","args":{"_genkey_0":"X-Request-Foo"}}
```
#### 2.13 重写响应头
```
将请求 /42?user=ford&password=omg!what&flag=true, 改为 /42?
user=ford&password=***&flag=true
yml配置
- RewriteResponseHeader = X-Response-Foo,password=[^&]+,password=***
 
json配置
{"name":"RewriteResponseHeader","args":{"_genkey_0":"X-Response-Foo","_genkey_1":"password=[^&]+","_genkey_2":"password=***"}}
```
#### 2.14 重设请求路径
```
请求/foo/bar，在接下来的处理中被改为/bar
yml配置
- SetPath =/{segment}
 
json配置
{"name":"SetPath","args":{"_genkey_0":"/{segment}"}}
```
#### 2.15 设置响应头
```
yml配置
- SetResponseHeader =X-Request-Foo,Bar
 
json配置
{"name":"SetResponseHeader","args":{"_genkey_0":"X-Response-Foo","_genkey_1":"Bar"}}
```
#### 2.16 设置Http状态
```
yml配置
- name: SetStatus
  args:
      status: 401
 
json配置
{"name":"SetStatus","args":{"_genkey_0":"302"}}
```
#### 2.17 设置文件传输大小
```
yml配置
 - name: RequestSize
   args:
       maxSize: 5000000
 
json配置
{"name":"RequestSize","args":{"_genkey_0":"5000000"}}
```
#### 2.18 失败重试
```
yml配置
- name: Retry
  args:
      retries: 3
      statuses: BAD_GATEWAY
 
json配置
{"name":"Retry","args":{"retries":"3","statuses":"BAD_GATEWAY"}} 
```


[原文链接：https://blog.csdn.net/shanzifeiwu/article/details/104795639/](https://blog.csdn.net/shanzifeiwu/article/details/104795639/)