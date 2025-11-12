package SpringMiniProject.SeSAC.MeetingRoom.Management.Domain;

import java.time.LocalDateTime;

public class Reservation {

    //dto를 사용해야 할 이유는 따로 없는 거 같다 => 사용해야 됨
    //reservation마다 id가 구분되어야 할 필요도 없는 거 같고 -> 수정, 삭제는 passWord 를 활용
    private LocalDateTime assignDateTime; //사용자로부터는 시작 날짜 / 시작 시간/ 사용 시간 세 가지를 입력받고
    private LocalDateTime dueDateTime; //startDateTime과 endDateTime은 시작 날짜에 시작시간을 더하고 사용 시간을 더해 내부에서 처리

    private Long meetingRoomId;
    private int assignYear;
    private int assignMonth;
    private int assignDay;
    private int assignHour;
    private int totalUsingHours;
    private String assignUserName;
    private String assignUserPhoneNumber;
    private String assignPassWord;

    public void generateDummyMeetingRoom() {
        //todo
        //datatime 파싱할 때 이상 값 예외 처리 확인
        this.meetingRoomId = 1111L;
        this.assignYear = 9999; //이거 입력 값 주어진 게 있나? -> 어떤 예외 던지는지 획인
        this.assignMonth = 12;
        this.assignHour = 12;
        this.assignDay = 31;
        this.totalUsingHours = 3;
        this.assignUserName = "test";
        this.assignUserPhoneNumber = "test";
        this.assignPassWord = "test";

        this.assignDateTime = LocalDateTime.of(assignYear, assignMonth, assignHour, 0, 0);
        this.dueDateTime = assignDateTime.plusHours(totalUsingHours);

    }

    //startDateTime, endDateTime 두 필드를 동시에 ModelMapper를 통해 만드려고 했는데 뭔가 메소드 이름 보고 매핑 못할 것 같은 쎼함이 든다.
    //곱게 setter 자동 생성을 만들어야겠다
    public void setAssignDateTime() {
        System.out.println("Reservation - setStartDateTime() : \nyear = "+assignYear+", month = "+assignMonth+", day = "+assignDay);
        this.assignDateTime = LocalDateTime.of(assignYear, assignMonth, assignDay, assignHour, 0);
    }

    public void setDueDateTime() {
        this.dueDateTime = this.assignDateTime.plusHours(totalUsingHours);
    }

    public void setMeetingRoomId(Long meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "startDateTime=" + assignDateTime +
                ", endDateTime=" + dueDateTime +
                ", meetingRoomId=" + meetingRoomId +
                ", assignedYear=" + assignYear +
                ", assignedMonth=" + assignMonth +
                ", assignedDay=" + assignDay +
                ", assignedHour=" + assignHour +
                ", totalUsingHours=" + totalUsingHours +
                ", assignedUserName='" + assignUserName + '\'' +
                ", assignedUserPhoneNumber='" + assignUserPhoneNumber + '\'' +
                ", passWord='" + assignPassWord + '\'' +
                '}';
    }
    public boolean checkAuthorization(RemoveReservation removeReservation) {
        removeReservation.setAssignDateTime();
        return removeReservation.checkAuthorization(this.assignDateTime, this.assignPassWord, this.assignUserName);
    }

    //새로 등록하려는 newReservation의 사용 시작, 끝 값을 매개변수로 입력받고
    //기존의 Reservation에 이 함수를 호출해 newReservation 의 예약 시작 끝 일정 사이에 일정이 존재하는 지 확인한다.
    public void checkReservationCollision(Reservation newReservation) {
        /*
        (this.startDateTime.equals(newReservation.startDateTime)
                ||this.endDateTime.equals(newReservation.endDateTime)
                ||(this.startDateTime.isAfter(newReservation.startDateTime)&&this.startDateTime.isBefore(newReservation.endDateTime))
                ||(this.endDateTime.isAfter(newReservation.startDateTime)&&this.endDateTime.isBefore(newReservation.endDateTime))
         */
        if (!(newReservation.dueDateTime.isBefore(this.assignDateTime)
                || newReservation.dueDateTime.equals(this.assignDateTime)
                || newReservation.assignDateTime.isAfter(this.dueDateTime)
                || newReservation.assignDateTime.equals(this.dueDateTime))
        ) {
            throw new ReservationCollisionException(this.assignDateTime + "부터 " + this.dueDateTime + "까지 이미 예약되어있어 일정이 겹칩니다.");
        }
    }
}
