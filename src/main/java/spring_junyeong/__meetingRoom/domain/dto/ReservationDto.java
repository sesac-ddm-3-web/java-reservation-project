package spring_junyeong.__meetingRoom.domain.dto;

import jakarta.validation.constraints.*;
import spring_junyeong.__meetingRoom.domain.validations.ValidTimeRange;

import java.time.LocalDateTime;

    /*
     API 요청을 위해 필요한, 오직 데이터 속성만을 담고 있는 순수한 객체의 모습"**
    - id (데이터베이스 ID) 같은 식별자가 포함되어 있지 않습니다.
    - createdAt, updatedAt과 같은 백엔드에서 생성해야 할 메타 데이터가 포함되어 있지 않습니다.
    - Room room; 와 같이 다른 Domain Entity 객체를 포함하고 있지 않습니다.
    */

@ValidTimeRange
public class ReservationDto {
    @NotNull(message = "시작 시간은 필수 입력값입니다.")
    LocalDateTime startTime;

    @NotNull(message = "종료 시간은 필수 입력값입니다.")
    LocalDateTime endTime;

    @NotBlank(message = "사용자 이름은 필수 입력값입니다.")
    @Size(max = 15, message = "이름은 15자를 초과할 수 없습니다.")
    String userName;

    @NotBlank(message = "전화번호는 필수 입력값입니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",
            message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
    String phoneNumber;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 4, max = 8, message = "비밀번호는 4자 이상 8자 이하여야 합니다.")
    String password;

    @NotNull(message = "참여 인원은 필수 입력값입니다.")
    @Min(value = 1, message = "참여 인원은 최소 1명 이상이어야 합니다.")
    @Max(value = 50, message = "참여 인원은 50명 이하이어야 합니다.")
    int participantCount;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public int getParticipantCount() {
        return participantCount;
    }
}