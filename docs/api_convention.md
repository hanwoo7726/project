# API 컨벤션

배달 주문 관리 플랫폼 팀 프로젝트의 REST API 설계 규칙입니다.

---

## 1. URL 기본 구조

```
http://{SERVER_URL}/api/v1/{resource}
```

- 모든 API는 `/api/v1` 을 prefix로 사용
- `api` : API 요청임을 명시
- `v1` : API 버전. 큰 변경이 생기면 `v2`로 분리 (기존 버전 유지)

**예시**

```
POST   /api/v1/products
GET    /api/v1/products/{productId}
PATCH  /api/v1/products/{productId}
DELETE /api/v1/products/{productId}
```

---

## 2. 리소스 네이밍 규칙

- 리소스는 **복수형 명사** 사용: `products`, `orders`, `stores` (O) / `product`, `getProduct` (X)
- URL에 **동사 사용 금지** — 행위는 HTTP 메서드로 표현
  - `POST /api/v1/products` (O)  /  `POST /api/v1/createProduct` (X)
- 단어 구분은 **하이픈(-)** 사용 (언더스코어 X)
  - `/api/v1/ai-logs` (O)  /  `/api/v1/ai_logs` (X)
- 경로 변수는 camelCase: `/api/v1/products/{productId}`

**관리자 전용 API**는 `admin` 을 붙임

```
GET /api/v1/admin/products     전체 상품 조회 (관리자)
GET /api/v1/admin/users        전체 회원 조회 (관리자)
```

---

## 3. HTTP 메서드 규칙

| 메서드 | 용도 | 예시 |
|---|---|---|
| `GET` | 조회 | `GET /api/v1/products` |
| `POST` | 생성 | `POST /api/v1/products` |
| `PATCH` | 부분 수정 | `PATCH /api/v1/products/{id}` |
| `PUT` | 전체 수정 |
| `DELETE` | 삭제 (Soft Delete) | `DELETE /api/v1/products/{id}` |

- 부분 수정은 `PATCH`를 기본으로 사용 (전달된 필드만 수정)
- 삭제는 물리 삭제가 아닌 **Soft Delete** (deleted_at 기록)

---

## 4. 공통 응답 포맷

모든 응답은 아래 형식으로 통일합니다.

**성공 응답**

```json
{
  "code": 200,
  "message": "성공",
  "data": { }
}
```

**목록 응답 (paging)**

```json
{
  "code": 200,
  "message": "성공",
  "data": {
    "content": [ ],
    "totalElements": 42,
    "totalPages": 5,
    "number": 0
  }
}
```

**에러 응답**

```json
{
  "code": 400,
  "message": "상품명은 필수입니다.",
  "data": null
}
```

---

## 5. HTTP 상태 코드

| 코드 | 상황 |
|---|---|
| `200 OK` | 조회/수정/삭제 성공 |
| `201 Created` | 생성 성공 |
| `400 Bad Request` | 유효성 검사 실패 (필수값 누락, 형식 오류) |
| `401 Unauthorized` | 인증 실패 (토큰 없음/만료) |
| `403 Forbidden` | 권한 없음 (인증됐으나 접근 불가) |
| `404 Not Found` | 리소스 없음 |
| `409 Conflict` | 중복 (이미 존재하는 username 등) |
| `500 Internal Server Error` | 서버 내부 오류 |

---

## 6. 요청/응답 필드 네이밍

- 요청·응답 JSON 필드는 **camelCase** 사용
  - `productId`, `storeId`, `createdAt` (O)
  - `product_id`, `created_at` (X — DB 컬럼은 snake_case지만 JSON은 camelCase)
- Boolean 필드는 `is` 접두사 지양, 명확한 이름 사용: `isHidden` 정도는 허용

---

## 7. 페이징 / 정렬 / 검색

**쿼리 파라미터로 전달**

```
GET /api/v1/products?keyword=만두&page=0&size=10&sort=createdAt,desc
```

| 파라미터 | 설명 |
|---|---|
| `page` | 페이지 번호 (0부터 시작) |
| `size` | 페이지 크기 — **10 / 30 / 50만 허용**, 그 외 값은 10으로 고정 |
| `sort` | 정렬 기준 (기본: `createdAt,desc` — 생성일 최신순) |
| `keyword` | 검색어 (도메인별 검색 대상 필드에 적용) |

- 정렬 기본값은 **생성일순** (요구사항 준수)
- 검색 조건과 정렬은 모든 목록 조회 API에 공통 적용

---

## 8. 인증 / 권한

- 인증이 필요한 API는 헤더에 JWT 토큰 포함

```
Authorization: Bearer {jwt_token}
```

- 모든 컨트롤러 엔드포인트는 **로그인 및 권한 체크** 수행
- 권한 역할: `CUSTOMER`(손님), `OWNER`(가게주인), `MANAGER`·`MASTER`(관리자)
- 각 API 명세에 **필요 권한(Role Requirement)**을 명시

---

## 9. API 명세서 작성 규칙

각 도메인 담당자는 자신의 API를 아래 항목을 포함하여 문서화합니다.

- 메서드 + URL
- Request Header (Authorization, 필요 권한)
- Request Elements (파라미터/타입/필수여부/설명)
- 요청 예시
- Response Elements
- 응답 예시 (성공 + 주요 에러)
---
