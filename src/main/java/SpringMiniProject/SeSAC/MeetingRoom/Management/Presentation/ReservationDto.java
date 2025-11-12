package SpringMiniProject.SeSAC.MeetingRoom.Management.Presentation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ReservationDto {

    //dto를 사용해야 할 이유는 따로 없는 거 같다 => 시작 끝 일정은 사용자로부터 바로 입력되는게 아니라 내부에서 따로 계산해야한다고 생각해 Dto 사용함
    //reservation마다 id가 구분되어야 할 필요도 없는 거 같고 -> 수정, 삭제는 passWord 를 활용

    //강사님이 Validation anotation 잘못쓰고 있다고 말씀해주셨는데
    //찾아보니 @NotBlank는 String 에만 사용할 수 있어
    //지금처럼 int에 @NotBlank를 적어놓게 되면 유효성 검사의 결과로 디폴트 값 0이 들어가게 됨
    //그럼 밑에 String들은 잘 들어갔어야 하는 거 아닌가

    @Min(0)
    private Long meetingRoomId;
    @Min(value = 2025, message = "연도는 2025 이상이어야 합니다.")
    private int assignYear;
    @Min(1)@Max(12)
    private int assignMonth;
    //todo
    //일단 31일까지 입력받고 그 외 유효성 검사는 나중에 Service에서 처리해야할 듯
    @Min(1)@Max(31)
    private int assignDay;
    @Min(0)@Max(23)
    private int assignHour;
    @Min(0)@Max(24)
    private int totalUsingHours;
    @NotBlank(message = "예약자 이름은 필수입니다.")
    private String assignUserName;
    @NotBlank(message = "예약자 전화번호는 필수입니다.")
    private String assignUserPhoneNumber;
    @NotBlank(message = "예약 비밀번호는 필수입니다.")
    private String assignPassWord;

    @Override
    public String toString() {
        return "ReservationDto{" +
                "meetingRoomId=" + meetingRoomId +
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
}
