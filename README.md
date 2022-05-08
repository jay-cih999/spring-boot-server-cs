# spring-boot-server-qna
Q&A Service server 입니다.

## 고객문의 접수 & 답변 서버 개발 입니다.

### 1. 프로젝트 구성(Server)
* Environment
 
종류|Stack|
--|--|
|Language | Java 11 |
|Framework | springBoot |
|DB | H2 Database|
|Build Tool | Maven
|Persistence Farmework | Mybatis | 
| IDE | STS

* DB ERD
* ![DB ERD](https://user-images.githubusercontent.com/31990955/167288350-c0db65d1-4c90-4cf5-b1fa-72bbeeb17b58.jpg)
* API 명세

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

* 고객용 문의 등록 : Web 화면에서 작성한 내용을 Server 측으로 전달하여 DB에 Insert 하는 방식으로 처리
* 고객용 문의 조회 : ID값을 직접 입력 하기 때문에 전체 목록을 출력하며, 검색 조건을 주어 원하는 값을 확인 할 수 있도록 처리
* 상담사용 문의 조회 : 최초 진입 시에는 등록된 목록만 보여주며, 상태 검색을 추가하여 할당, 완료된 목록을 확인 할 수 있도록 처리
* 상담사용 문의 할당 : 문의가 등록 상태일 경우 할당 할 수 있도록 처리
* 상담사용 문의 답변 : 문의가 할당 상태이면서, 상담사가 자신일 경우에만 답변을 할 수 있도록 처리
* 상담사용 로그인 : id/password 가 일치하는 경우 로그인 되도록 처리(password 암호화 처리 X)
