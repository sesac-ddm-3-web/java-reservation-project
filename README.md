
# 1. 요구사항

“회의실(또는 스터디룸)”과 “예약”이라는 두 가지 핵심 도메인을 자유롭게 설계해야 합니다.

첫 요구사항에 있는 기본 기능을 구현하고 완성한 교육생은 2, 3, 4번까지 순서대로 진행하면 됩니다.

시간은 17시 ~ 21시 30분까지 진행할 예정입니다.

## 회의실 요구사항

- 애플리케이션이 시작될 때, 미리 정의된 여러 개의 회의실 정보가 인메모리 컬렉션에 저장되어 있어야 합니다.
- **[API]** 전체 회의실 목록을 조회하는 API를 구현해야 합니다.

## 예약 요구사항

- 예약 정보는 특정 회의실 시작 시간, 종료 시간, 예약자 정보(아래 추가 설명)을 포함해야 합니다.
- 비회원 예약제로 진행하여야 합니다. 아래 정보는 필수로 있어야 합니다.
  - 예약자명
  - 전화번호
  - 비밀번호 (예약 수정, 삭제 시 본인 확인용)
- **[API]** 새 예약을 생성하는 API를 구현해야 합니다.
- **[API]** 특정 회의실의 모든 예약 현황을 조회하는 API를 구현해야 합니다.
- **[API]** 특정 예약을 취소(삭제)하는 API를 구현해야 합니다.

## 핵심 비즈니스 로직 및 제약 조건

- 예약 충돌 감지
  - 새 예약을 생성할 때, 요청된 회의실의 요청된 시간대가 기존 다른 예약과 겹치는지 확인해야 합니다.
  - 시간이 겹치는 예약이 이미 존재한다면, 예약을 생성하지 않고 HTTP 상태 코드로 응답해야 합니다.
- 예약 취소 시 비밀번호 검증
  - API 호출 시, 비밀번호를 함께 받아야 합니다.
    - 비밀번호를 받는 방식은 자율적으로 설계하시면 됩니다.
  - 저장된 예약의 비밀번호와 일치하는 경우에만 삭제가 가능합니다.
  - 비밀번호가 틀릴 경우, HTTP status로 응답해야 합니다.
- 유효성 검사
  - 예약 요청 시, 종료 시간은 시작 시간보다 늦어야 합니다.
  - 예약자 정보 등 필수 값은 비어있지 않아야 합니다.
- 예외 처리
  - 존재하지 않는 회의실 ID로 예약하려는 경우
  - 예약 시간이 겹치는 경우
  - 유효성 검사 실패한 경우

---

# **회의실 예약 시스템**

## **개요**

- 간단한 **인메모리 기반 회의실 예약 시스템**
- 데이터베이스 없이 애플리케이션 실행 시 회의실/예약 데이터 초기화(**더미 데이터**)
- **Spring Boot 3.5.7**, **Java 17**, **Gradle** 사용
  - Gradle: 빠른 빌드와 멀티모듈 확장성
  - Java 17: Spring Boot 최소 호환 버전(LTS 안정성 확보)

## **도메인**

### **1. 회의실(Room)**

- 필드

| **이름** | **타입** | **설명** |
| --- | --- | --- |
| id | Long | 회의실 고유 ID |
| name | String | 회의실 이름 |
| openAt | LocalTime | 회의실 운영(예약) 시작 시간 |
| closeAt | LocalTime | 회의실 운영 마감 시간 |
- 메서드

| **메서드명** | **설명** |
| --- | --- |
| validateReservationAllowed(LocalTime start, LocalTime end) | 특정 예약이 회의실 운영 시간 범위 내에 있는지 검증 |

### **2. 예약(Reservation) :** 비회원 예약 시스템

- 필드

| **이름** | **타입** | **설명** |
| --- | --- | --- |
| id | Long | 예약 고유 ID |
| roomId | Long | 예약한 회의실 ID |
| startAt | LocalDateTime | 예약 시작 시간 (분 단위 정규화) |
| endAt | LocalDateTime | 예약 종료 시간 (분 단위 정규화) |
| bookerName | String | 예약자 이름 |
| bookerPhone | String | 예약자 전화번호 (010-xxxx-xxxx 형식) |
| bookerPassword | String | 예약자 비밀번호 (4자리 숫자) |
- 메서드

| **메서드명** | **설명** |
| --- | --- |
| create(Long roomId, LocalDateTime startAt, LocalDateTime endAt, String name, String phone, String bookerPassword) | 예약 생성 시 유효성 검증 수행 후 Reservation 객체 생성 |
| validatePassword(String password) | 예약자 비밀번호 일치 여부 검증 (취소 시 본인 확인용) |
| overlaps(LocalDateTime startAt, LocalDateTime endAt) | 기존 예약이 요청 예약와 겹치는지 여부 판단 |
| sameRoomId(Long roomId) | 특정 회의실에 속한 예약인지 확인 |
| isBlank(String s) | 문자열이 비어있는지 단순 검증 |

### **예약 도메인 규칙**

- **예약은 항상 유효한 상태로만 생성되어야 함.**

  → 생성자에서 모든 유효성 검사 수행.

- **시간은 분 단위로 정규화되어 저장됨.**

  → 초·나노초 차이에 의한 중복 방지.

- **비회원 예약 시스템**이므로 인증 대신 **비밀번호 검증 방식** 사용.
- **예약 시간 중복 불가**

  → overlaps()를 통해 기존 예약과 겹치는 경우 예외 발생.


---

## **공통 유틸리티**

### **TimeUtils**

: LocalDateTime / LocalTime 객체의 정규화를 담당하는 유틸리티 클래스

| **메서드** | **설명** |
| --- | --- |
| snapToMinute(LocalDateTime time) | 초(second)와 나노초(nano)를 제거하여 “분 단위”로 정규화 |
| snapToMinute(LocalTime time) | 시간 비교 시 오차 방지를 위해 초/나노초 제거 |

<사용 예시>

```java
LocalDateTime normalized = TimeUtils.snapToMinute(startAt);
```

**설명**

- 예약 및 회의실 운영시간 비교 시 `09:00:00.123` 같은 미세한 차이를 방지
- 모든 시간 비교를 **분 단위로 일관성 있게 처리**하도록 보장

### **2. ValidationUtils**

: 문자열, 숫자 등 도메인 필드 값의 유효성을 공통적으로 검사하는 유틸리티 클래스

| **메서드** | **설명** |
| --- | --- |
| isBlank(String s) | 문자열이 null이거나 공백인지 확인 |

---

## **API 명세서**

### **GET** /rooms : 전체 회의실 목록 조회

- request

```json

```

- response

```json
[
  {
    "id": 1,
    "name": "회의실 1",
    "openTime": "09:00",
    "closeTime": "20:00"
  },
  {
    "id": 2,
    "name": "회의실 2",
    "openTime": "09:00",
    "closeTime": "20:00"
  }
]
```

| **상태 코드** | **설명** |
| --- | --- |
| 200 OK | 회의실 목록 조회 성공 |

### **GET** /rooms/{id}/reservations : 특정 회의실 예약 현황 조회

- request

```json

```

- response

```json
[
  {
    "id": 1,
    "startTime": "2025-11-10 09:00",
    "endTime": "2025-11-10 11:00"
  },
  {
    "id": 2,
    "startTime": "2025-11-10 12:00",
    "endTime": "2025-11-10 14:00"
  }
]
```

- error

| **상태 코드** | **설명** |
| --- | --- |
| 200 OK | 예약 목록 조회 성공 |
| 404 Not Found | 존재하지 않는 회의실 |

```json
{
    "message": "회의실 12을 찾지 못했습니다."
}
```

### **POST** /rooms/{id}/reservations : 새 예약 생성

- request

```json
{
  "bookerName": "홍길동",
  "bookerPassword": "1234",
  "bookerPhone": "010-1234-5678",
  "startTime": "2025-11-10 14:00",
  "endTime": "2025-11-10 15:00"
}
```

- response

```json
{
  "id": 21
}
```

- error

| **상태 코드** | **설명** |
| --- | --- |
| 201 Created | 예약 생성 성공 |
| 400 Bad Request | 잘못된 요청, 필드 누락, 예약 시간 유효하지 않음 |
| 404 Not Found | 존재하지 않는 회의실 |
| 409 Conflict | 예약 시간이 기존 예약과 겹침 |

```json
{
    "message": "비밀번호는 4자리 숫자여야 합니다."
}
```

```json
{
    "message": "전화번호는 010-xxxx-xxxx 형식이어야 합니다."
}
```

### **DELETE** /rooms/{id}/reservations/{reservationId} : 예약 취소

- request

```json
{
  "bookerPassword": "1234"
}
```

- response

```json

```

- error

| **상태 코드** | **설명** |
| --- | --- |
| 204 No Content | 예약 취소 성공 |
| 400 Bad Request | 요청 형식 오류 |
| 401 Unauthorized | 비밀번호 불일치 |
| 404 Not Found | 존재하지 않는 회의실 또는 예약 |

```json
{
    "message": "예약 확인이 되지 않았습니다. 예약 정보를 다시 입력해주세요"
}
```

```json
{
    "message": "비밀번호가 일치하지 않습니다"
}
```

—> 11/11

---