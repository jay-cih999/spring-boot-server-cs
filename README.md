# spring-boot-server-cs
customer sevice server 입니다.

## 고객문의 접수 & 답변 서버 개발 입니다.

### 1. 프로젝트 구성(Server)
---
종류|Stack|
--|--|
 |Language | Java 11 |
 |Framework | springBoot |
 |DB | H2 Database|
 |Build Tool | Maven
 |Persistence Farmework | Mybatis | 
 | IDE | STS

---
### 2. 실행 방법
---

1. Source Code Download
```
git clone 
```
2. maven build
```
cd spring-boot-server-qna
mvnw build package
```
3. 실행
```
mvnw spring-boot:run
```
4. 접속
```
chrome 에서 http://localhost:8080/qna 수행
Q&A 목록 데이터 출력됨
```
5. 단위테스트
```
mwnw test
```
---

### 3.문제해결 전략
