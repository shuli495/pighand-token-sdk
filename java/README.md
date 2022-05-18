# pighand-token-sdk for Java

pighand-token：统一管理三方access token。支持微信、支付宝、飞书

- [pighand-token](https://github.com/shuli495/pighand-token)

## 使用方法

1. Application启用配置

```
@EnableConfigurationProperties(PighandConfiguration.class)
```

2. application.yml增加参数

```
pighand:
  token:
    host: http://127.0.0.1:3050
    disabled-redis-active:
      - dev
      - test
```

## 运行逻辑

1. 如果项目中配redis，先在redis中查询是否有可用token；
2. 如果禁用指定环境redis查询，则跳过redis查询；
3. 如果没有redis或redis中不存在token，通过接口查询。

### 参数说明

1. host

```
pighand-token 服务地址
```

2. disabled-redis-active

```
禁用redis查询token的环境。

默认取"spring.profiles.active"的值；支持"all"，表示禁用所有环境。

例如：dev，表示dev环境跳过redis，直接通过接口查询token
```