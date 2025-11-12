# 회의실 예약 실습

## 실습 환경

Build tool: Gradle

선정 이유: 빌드 속도가 maven의 비해 빠르다.

Language: Java17

선정이유: Java21의 가장 큰 변화가 Virtual Thread가 도입된 것인데 현재 하는 실습에서는 필요없다 생각해서
원래 사용하던 Java17로 골랐습니다.

Spring Boot: 3.5.7

인메모리 컬렉션 사용

## 회의실 요구사항

애플리케이션이 시작될 때, 미리 정의된 여러 개의 회의실 정보가 인메모리 컬렉션에 저장되어 있어야 합니다.

- **회의실 domain**

  - id
  - name

- **요구사항 구현**
  - ListMeetingRoomRepository에 PostConstruct에너테이션을 붙인 init메서드로 List에 MeetingRoom 인스턴스들을 저장했습니다.

**[API]** 전체 회의실 목록을 조회하는 API를 구현해야 합니다.

- GET /meetingRooms

```json
  - response
    [
        {
            "id": 1,
            "name": "회의실 1"
        },
        {
            "id": 2,
            "name": "회의실 2"
        },
        {
            "id": 3,
            "name": "회의실 3"
        },
        {
            "id": 4,
            "name": "회의실 4"
        }
    ]
```

## 예약 요구사항

- 예약 정보는 특정 회의실 시작 시간, 종료 시간, 예약자 정보(아래 추가 설명)을 포함해야 합니다.
- 비회원 예약제로 진행하여야 합니다. 아래 정보는 필수로 있어야 합니다.

- **예약 domain**
  - id
  - roomId
  - startTime
  - endTime
  - name
  - phoneNumber
  - password

**[API]** 새 예약을 생성하는 API를 구현해야 합니다.

- Post /meetingRooms/{roomId}/reservations

```json
  -request
  {
    "startTime":"2025-11-20T14:18:00",
    "endTime":"2025-11-20T14:20:00",
    "name":"홍찬용",
    "phoneNumber":"01012345678",
    "password":"qwer"
  }
  - response
    {
        "id": 1,
        "roomId": 2,
        "startTime": "2025-11-20T14:18:00",
        "endTime": "2025-11-20T14:20:00",
        "name": "홍찬용",
        "phoneNumber": "01012345678",
        "password": "qwer"
    }
```

**[API]** 특정 회의실의 모든 예약 현황을 조회하는 API를 구현해야 합니다.

- GET /meetingRooms/{roomId}/reservations

```json
  -response
  [
    {
    "id": 1,
    "roomId": 2,
    "startTime": "2025-11-20T14:18:00",
    "endTime": "2025-11-20T14:20:00",
    "name": "홍찬용",
    "phoneNumber": "01012345678",
    "password": "qwer"
    }
  ]
```

**[API]** 특정 예약을 취소(삭제)하는 API를 구현해야 합니다.

- DELETE /meetingRooms/{roomId}/reservations/{reservationId}

```json
  -request
  {
    "password":"qwer"
  }
```
