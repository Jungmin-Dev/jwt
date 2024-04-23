application.yml 

```
server:
  port: 8081
spring:

  # Redis
  cache:
    type: redis
  data:
    redis:
      host: localhost
      port: 6379

  # DataSource Setting
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/jwt?useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul
    username: 
    password: 

  # JPA Setting
  jpa:
    hibernate:
      ddl-auto: none
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

# JWT secretKey 64자리
jwt:
  secretKey: testsecrettestsecrettestsecrettestsecrettestsecrettestsecretKey1
```

터미널에서 redis-cli 명령어를 입력하여 레디스 접속 가능

keys * 명령어로 캐시 데이터 확인 가능


