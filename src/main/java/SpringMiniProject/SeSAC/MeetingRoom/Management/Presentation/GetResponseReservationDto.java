package SpringMiniProject.SeSAC.MeetingRoom.Management.Presentation;

import java.time.LocalDateTime;

public class GetResponseReservationDto {
    //조회 했을 때 필요한 정보만 노출
    private Long meetingRoomId;
    private LocalDateTime assignDateTime;
    private LocalDateTime dueDateTime;
}
