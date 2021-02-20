# 助人者論壇專案
這是一個藉著練習前後端分離，並以助人者論壇為題的專案前端以VueCLI3，後端以SpringBoot為主的專案，也希望這之後能順利上線，歡迎code review 或發pr，或是意外被助人工作者看到，能提供些意見。
專案API文檔: [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

預計開發論壇
功能有:
1. 登入功能(留言、發文用)
2. 留言板
3. 爬蟲爬靠北社工?
4. 發文區
5. 管理員後台
6. 其他再想想

# 預計使用技術:
### 前端:
- [x] Vue3
- [x] Bootstrap3/4
- [X] ElementUI
### 後端:
- [X] Spring boot
- [X] Spring Data JPA
- [X] Spring Security

### 其他:
- [X] swagger
- [X] gradle
- [X] redis
### DB:
* MySQL or PostgreSQL(目前使用Mysql)

# 安裝步驟
此處為後端專案，需要具備以下環境：
1. Java JDK 11
2. [gradle](https://www.1ju.org/gradle/how-install-gradle-windows)(如果在Eclipse，需安裝[外掛](https://rx1226.pixnet.net/blog/post/321583836))
3. [Lombok](https://polinwei.com/lombok-install-in-eclipse/)
4. MySQL(建立 ```social_work``` 資料庫)

如果有裝 Docker，可使用專案資料夾 resource下的 docker-compose來起DB跟redis，
   啟動方式如下：
```
//用terrminal 到docker-compose位置
docker-compose up -d
```

並在application.yml中，更改裡頭的環境變數

# 參考資料
* [Springdoc](https://waynestalk.com/springdoc-openapi-tutorial/)
* [Spring Security 概念](https://waynestalk.com/spring-security-architecture-explained/)
* [Spring Security](https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/300662/)


### 更新筆記
* 10/13 [Spring Security基礎設定](https://hackmd.io/aCPS0qYeQ6O2Wq7O1QsCBQ?both)
* 10/29、10/30 [解決Spring Security設定問題](https://hackmd.io/GDnU7AhJQkqEXOd5ICfJHA)
* 11/02 [SwaggerUI設定](https://hackmd.io/fsUVJPBSTwqQfi9UDs5uDA?both)
* 11/04-11/08 [前端:註冊頁面；後端:註冊API](https://hackmd.io/K8ci1BgXQKW1WMg9XUIw3Q?both)
* 11/09-11/12 [前端:會員資料預覽、編輯、Confirm Component；後端:查詢會員資料、更新會員資料相關API、ExceptionHandler新增會員資料改動產生的Exception](https://hackmd.io/1SIfOeRNSoanEDjwEyRTJg)
* 11/13-11/28 [refresh jwt token part1](https://hackmd.io/fh1njno7QQ6Y8IKfJBoviQ)
* [refresh jwt token part2](https://hackmd.io/g7-IcniJRXyE6lNkUqE07g)
* Redis [簡介](https://hackmd.io/ocq7AnMnQTeYID1q2FztOA)