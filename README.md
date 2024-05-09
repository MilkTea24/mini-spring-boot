# Mini-SpringBoot 샘플 프로젝트 🌱
Mini-SpringBoot JAR 파일이 의존성으로 추가되어 있어 Mini-SpringBoot 프로젝트를 직접 실행해볼 수 있는 프로젝트입니다.
자세한 프로젝트의 정보는 [mini-spring-boot 저장소](https://github.com/MilkTea24/mini-spring-boot)를 참조하세요.

# 샘플 데이터 🌱
## 첫 번째 사용자 추가
> **POST http://localhost:9090/register**
### ResquestBody
```
{
"username":"user1",
"email":"user1@naver.com",
"password":"user1234",
"age":25
}
```

### ResponseBody
```
{
"userId": 0
}
```

## 두 번째 사용자 추가
>  POST http://localhost:9090/register
### RequestBody
```
{
"username":"user2",
"email":"user2@naver.com",
"password":"user1234",
"age":22
}
```

### ResponseBody
```
{
"userId": 1
}
```

## 두 번째 사용자 자세한 정보 출력
> GET http://localhost:9090/user/1?isDetailed=true

### ResponseBody
```
{
"userId": 1,
"username": "user2",
"email": "user2@naver.com",
"password": "user1234",
"age": 22
}
```

## 두 번째 사용자 간단한 정보 출력
> GET http://localhost:9090/user/1

### ResponseBody
```
{
"userId": 1,
"username": "user1",
"email": null,
"password": null,
"age": 0
}
```
