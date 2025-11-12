package SpringMiniProject.SeSAC.MeetingRoom.Management.Presentation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RemoveReservationDto {
    @Min(value = 2025, message = "연도는 2025 이상이어야 합니다.")
    private int assignYear;
    @Min(1)@Max(12)
    private int assignMonth;
    @Min(1)@Max(31)
    private int assignDay;
    @Min(0)@Max(23)
    private int assignHour;
    @NotBlank(message = "예약자 이름은 필수입니다.")
    private String assignUserName;
    @NotBlank(message = "예약 비밀번호는 필수입니다.")
    private String assignPassWord;
}
