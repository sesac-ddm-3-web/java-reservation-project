# 회의실 예약 서비스

## 요구사항

### 도메인

#### 회의실 (MeetingRoom)

- 정보
  - ID
  - 이름
  - 위치
  - 최대 수용 인원
- 기능
  - 회의실은 ID로 식별 가능해야 한다.
  - 회의실 이름을 변경할 수 있다.
  - 회의실 위치를 변경할 수 있다.
  - 참석 인원을 모두 수용할 수 있는지 확인할 수 있다.
- 제약 조건
  - 이름, 위치, 최대 수용 인원은 반드시 가지고 있어야 한다.

#### 예약 (Reservation)

- 정보
  - ID 
  - 예약한 회의실 ID
  - 회의실 사용 시작 시간
  - 회의실 사용 종료 시간
  - 예약자 정보 (VO)
- 기능
  - 예약은 ID로 식별 가능해야 한다.
  - 예약자 정보로 명시한 비밀번호가 일치하는지 확인할 수 있다.

#### 비품 (Equipment)

- 정보
  - ID
  - 이름 
  - 수량 
  - 해당 비품이 비치된 회의실 ID
- 기능
  - 비품은 ID로 식별 가능해야 한다.
  - 지정한 개수만큼 사용할 수 있는지 여부를 확인할 수 있다.

### 값 객체 (Value Objects)

#### 회의실 ID (MeetingRoomId)
- 회의실 ID를 표현하기 위한 VO
- ID를 부여받기 전 비어 있는 ID를 표현하기 위한 VO

#### 회의실 위치 (MeetingRoomLocation)
- 회의실 위치를 표현하기 위한 VO

#### 예약 ID (ReservationId)
- 예약 ID를 표현하기 위한 VO
- ID를 부여받기 전 비어 있는 ID를 표현하기 위한 VO

#### 비회원 예약자 (Organizer)
- 비회원 예약자를 표현하기 위한 VO
- 오로지 비회원으로만 예약이 가능하며, 같은 이름을 가지고 있다고 하더라도 동일한 사용자가 아닐 수 있으므로 VO로 표현

#### 비품 사용 정보 (EquipmentUsage)
- 예약 영역에서 비품 사용 정보를 표현하기 위한 VO
- 기존 비품 도메인(Equipment)과 특정 시간에 종속적인, 예약 영역에서의 비품 사용 정보를 분리하기 위해 사용

### 컬렉션

#### 회의실 목록 (MeetingRooms)

- 기능
  - 회의실 ID로 해당 회의실을 조회할 수 있다.
  - 회의실 이름으로 해당 회의실을 조회할 수 있다.
  - 회의실 위치로 해당 회의실을 조회할 수 있다.
  - 지정한 참석 인원을 수용할 수 있는 회의실을 모두 조회할 수 있다.

#### 예약 목록 (Reservations)

- 기능
  - 특정 예약을 추가할 수 있는지 확인할 수 있다.
  - 특정 예약을 삭제할 수 있는지 확인할 수 있다.
  - 반복 예약을 추가할 수 있는지 확인할 수 있다.
- 제약 조건
  - 새로운 예약은 기존의 다른 예약과 시간이 겹치지 않아야 한다.
  - 이미 사용 중인 예약이라면 취소할 수 없다.

#### 비품 목록 (Equipments)

- 기능
  - 특정 회의실의 비품을 원하는 만큼 사용할 수 있는지 확인할 수 있다.

#### 비품 사용 정보 목록 (EquipmentUsages)

- 기능
  - 특정 비품의 사용 수량을 반환할 수 있다.

### 레포지토리

#### 회의실 레포지토리 (MeetingRoomRepository)

- 설명
  - 회의실을 관리하는 레포지토리
  - 요구사항에 회의실을 추가/삭제하는 내용이 없으므로 단순 조회 기능만 제공
- 구현체 (InMemoryMeetingRoomRepository)
  - 인메모리에서 회의실을 관리하는 레포지토리
  - 생성자에서 미리 정의한 여러 개의 회의실 초기화

#### 예약 레포지토리 (ReservationRepository)

- 설명
  - 특정 회의실에 대한 예약을 관리하는 레포지토리
  - 저장 / 조회 / 삭제 기능 제공
- 구현체 (InMemoryReservationRepository)
  - 인메모리에서 예약을 관리하는 레포지토리

#### 비품 레포지토리 (EquipmentRepository)

- 설명
  - 특정 회의실에 대한 비품을 관리하는 레포지토리 
  - 요구사항에 비품을 추가/삭제하는 내용이 없으므로 단순 조회 기능만 제공
- 구현체 (InMemoryEquipmentRepository)
  - 인메모리에서 비품을 관리하는 레포지토리 
  - 생성자에서 미리 정의한 여러 개의 비품 초기화 

### 팩토리 

#### 예약 팩토리 (ReservationFactory)

- 설명
  - Reservation 생성 시 검증 및 조립을 수행하는 팩토리 
  - 내부적으로 레포지토리를 통해 일급 컬렉션을 조회해 검증 로직 수행

### API

#### 회의실 

##### 전체 회의실 조회
```text
GET /rooms
GET /rooms?attendeeCount={참석 인원}

Response:
{
  "meetingRooms": [
    {
      "id": 1,
      "name": "회의실 A",
      "floor": 3,
      "roomNumber": 1,
      "capacity": 10
    },
    {
      "id": 2,
      "name": "회의실 B",
      "floor": 3,
      "roomNumber": 21,
      "capacity": 20
    }
  ]
}

```

| 파라미터          | 타입  | 필수 여부 | 설명                       |
| ------------- | --- | ----- | ------------------------ |
| attendeeCount | int | 선택    | 지정한 인원을 수용할 수 있는 회의실만 조회 |

| 필드                        | 타입     | 설명     |
| ------------------------- | ------ | ------ |
| meetingRooms              | Array  | 회의실 목록 |
| meetingRooms[].id         | Long   | 회의실 ID |
| meetingRooms[].name       | String | 회의실 이름 |
| meetingRooms[].floor      | int    | 층수     |
| meetingRooms[].roomNumber | int    | 호실 번호  |

#### 비품

##### 전체 회의실 비품 조회 

```text
GET /equipments

Response:
{
  "equipments": {
    "1": [
      {
        "id": 1,
        "name": "빔 프로젝터",
        "quantity": 2
      },
      {
        "id": 2,
        "name": "화이트보드",
        "quantity": 1
      }
    ],
    "2": [
      {
        "id": 3,
        "name": "빔 프로젝터",
        "quantity": 1
      }
    ]
  }
}

```

| 필드                         | 타입     | 설명                     |
| -------------------------- | ------ | ---------------------- |
| equipments                 | Map    | 회의실별 비품 목록 (키: 회의실 ID) |
| equipments.{meetingRoomId} | Array  | 해당 회의실의 비품 목록          |
| equipments.{}.id           | Long   | 비품 ID                  |
| equipments.{}.name         | String | 비품 이름                  |
| equipments.{}.quantity     | int    | 보유 수량                  |

##### 특정 회의실 비품 조회 

```text
GET /equipments/{meetingRoomId}

Response:
{
  "equipments": [
    {
      "id": 1,
      "name": "빔 프로젝터",
      "quantity": 2
    },
    {
      "id": 2,
      "name": "화이트보드",
      "quantity": 1
    }
  ]
}

```

| 파라미터          | 타입   | 필수 여부 | 설명     |
| ------------- | ---- | ----- | ------ |
| meetingRoomId | Long | 필수    | 회의실 ID |

| 필드                    | 타입     | 설명    |
| --------------------- | ------ | ----- |
| equipments            | Array  | 비품 목록 |
| equipments[].id       | Long   | 비품 ID |
| equipments[].name     | String | 비품 이름 |
| equipments[].quantity | int    | 보유 수량 |

#### 예약

##### 예약 조회

```text
GET /rooms/{meetingRoomId}/reservations/{reservationId}

{
  "id": 1,
  "startTime": "2025-11-11T10:00:00",
  "endTime": "2025-11-11T12:00:00",
  "attendeeCount" : 5,
  "organizer": {
    "name": "예약자1",
    "phoneNumber": "010-1234-5678",
    "password": "1234"
  },
  "equipmentUsages": [
      {
          "id": 1,
          "name": "빔프로젝터",
          "quantity": 2
      }
  ]
}
```

| 필드                                   | 타입            | 설명           |
| ------------------------------------ | ------------- | ------------ |
| id                    | Long          | 예약 ID        |
| startTime             | LocalDateTime | 회의실 사용 시작 시간 |
| endTime               | LocalDateTime | 회의실 사용 종료 시간 |
| organizer             | Object        | 비회원 예약자 정보   |
| organizer.name        | String        | 이름           |
| organizer.phoneNumber | String        | 전화번호         |
| organizer.password    | String        | 비밀번호         |


##### 회의실 예약 조회

```text
GET /rooms/{meetingRoomId}/reservations

{
  "reservations": [
    {
      "id": 1,
      "startTime": "2025-11-11T10:00:00",
      "endTime": "2025-11-11T12:00:00",
      "attendeeCount" : 5,
      "organizer": {
        "name": "예약자1",
        "phoneNumber": "010-1234-5678",
        "password": "1234"
      }
    },
    {
      "id": 2,
      "startTime": "2025-11-11T14:00:00",
      "endTime": "2025-11-11T16:00:00",
      "attendeeCount" : 3,
      "organizer": {
        "name": "예약자2",
        "phoneNumber": "010-5678-1234",
        "password": "5678"
      }
    }
  ]
}
```

| 필드                                   | 타입            | 설명           |
| ------------------------------------ | ------------- | ------------ |
| reservations                         | Array         | 예약 목록        |
| reservations[].id                    | Long          | 예약 ID        |
| reservations[].startTime             | LocalDateTime | 회의실 사용 시작 시간 |
| reservations[].endTime               | LocalDateTime | 회의실 사용 종료 시간 |
| reservations[].organizer             | Object        | 비회원 예약자 정보   |
| reservations[].organizer.name        | String        | 이름           |
| reservations[].organizer.phoneNumber | String        | 전화번호         |
| reservations[].organizer.password    | String        | 비밀번호         |

##### 회의실 예약 

```text
POST /rooms/{meetingRoomId}/reservations

Request:
{
  "startTime": "2025-11-11T10:00:00",
  "endTime": "2025-11-11T12:00:00",
  "organizer": {
    "name": "홍길동",
    "phoneNumber": "010-1234-5678",
    "password": "1234"
  }
}

Response:
201 Created
Location: /rooms/{meetingRoomId}/reservations/{reservationId}

```

| 필드                    | 타입            | 필수 여부 | 설명           |
| --------------------- | ------------- | ----- | ------------ |
| startTime             | LocalDateTime | 필수    | 회의실 사용 시작 시간 |
| endTime               | LocalDateTime | 필수    | 회의실 사용 종료 시간 |
| organizer             | Object        | 필수    | 비회원 예약자 정보   |
| organizer.name        | String        | 필수    | 이름           |
| organizer.phoneNumber | String        | 필수    | 전화번호         |
| organizer.password    | String        | 필수    | 비밀번호         |

##### 회의실 반복 예약 
```text
POST /rooms/{meetingRoomId}/reservations/repeat

Request:
{
  "startTime": "2025-11-11T10:00:00",
  "endTime": "2025-11-11T12:00:00",
  "organizer": {
    "name": "홍길동",
    "phoneNumber": "010-1234-5678",
    "password": "1234"
  },
  "attendeeCount": 5,
  "frequency": "WEEKLY",
  "repeatCount": 4
}

Response:
201 Created
Location: /rooms/{meetingRoomId}/reservations

```

| 필드                    | 타입            | 필수 여부 | 설명                                     |
| --------------------- | ------------- | ----- | -------------------------------------- |
| startTime             | LocalDateTime | 필수    | 회의실 사용 시작 시간                           |
| endTime               | LocalDateTime | 필수    | 회의실 사용 종료 시간                           |
| organizer             | Object        | 필수    | 비회원 예약자 정보                             |
| organizer.name        | String        | 필수    | 이름                                     |
| organizer.phoneNumber | String        | 필수    | 전화번호                                   |
| organizer.password    | String        | 필수    | 비밀번호                                   |
| attendeeCount         | Integer       | 필수    | 참가 인원 (양수)                             |
| frequency             | String        | 필수    | 반복 주기 (DAILY, WEEKLY, MONTHLY, YEARLY) |
| repeatCount           | Integer       | 필수    | 반복 횟수 (양수)                             |

##### 회의실 예약 취소

```text
DELETE /rooms/{meetingRoomId}/reservations/{reservationId}

Request:
{
  "password": "1234"
}

Response:
204 No Content

```

| 필드       | 타입     | 필수 여부 | 설명   |
| -------- | ------ | ----- | ---- |
| password | String | 필수    | 비밀번호 |
