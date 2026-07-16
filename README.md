# 배달 주문 관리 플랫폼

Spring Boot 기반 배달 주문 관리 플랫폼 백엔드입니다.
회원/가게/상품/주문/장바구니/리뷰 도메인과 AI 상품 설명 생성 기능을 제공합니다.

## 기술 스택

| 구분 | 사용 기술 |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.16 |
| ORM | Spring Data JPA, QueryDSL 5.1.0 |
| DB | PostgreSQL |
| 인증/인가 | Spring Security, JWT (jjwt 0.13.0) |
| AI | Google Gemini API (gemini-2.5-flash) |
| Build | Gradle 8.14.5 |

## 아키텍처

모놀리식 + 3-Layered Architecture

```
com.sparta.project
├── address/         주소
├── auth/            인증 (로그인, JWT, 토큰 재발급)
├── cart/            장바구니
├── category/        음식 카테고리
├── order/           주문
├── product/         상품 + AI
├── review/          리뷰
├── store/           가게
├── storeFavorite/   가게 찜
├── user/            회원
└── global/          공통 (BaseEntity, 예외 처리, 설정)
```

각 도메인은 다음 구조를 따릅니다.

```
{domain}/
├── controller/      요청/응답 처리
├── service/         비즈니스 로직
├── repository/      데이터 접근
├── entity/          도메인 엔티티
└── dto/
    ├── request/
    └── response/
```

## 실행 방법

### 1. 사전 준비

PostgreSQL에 데이터베이스를 생성합니다.

```sql
CREATE DATABASE project_db;
```

### 2. 환경변수 설정

민감 정보는 환경변수로 주입합니다.

```bash
export DB_USERNAME=<DB 계정>
export DB_PASSWORD=<DB 비밀번호>
export JWT_SECRET_KEY=<JWT_SECRET_KEY>
export GEMINI_API_KEY=<Gemini API 키>
```

### 3. 실행

```bash
./gradlew bootRun
```

기본 포트는 `8080`이며, `spring.jpa.hibernate.ddl-auto=update` 설정으로 스키마가 자동 생성됩니다.

### 4. 테스트

```bash
./gradlew test
```