package SpringMiniProject.SeSAC.MeetingRoom.Management.Presentation;

import SpringMiniProject.SeSAC.MeetingRoom.Management.Application.ReservationService;
import SpringMiniProject.SeSAC.MeetingRoom.Management.Domain.NoReservationExistsException;
import SpringMiniProject.SeSAC.MeetingRoom.Management.Domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class ReservationController {

    Reservation dummyMeetingRoom = new Reservation();
    private ReservationService reservationService;

    @Autowired
    ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //이유는 모르겠는데 404 에러가 뜸 -> Application 이하의 디렉토리에 위치해야 하는데 바로 java 파일 아래 있었음
    @RequestMapping(value = "/reservations/{meetingRoomId}", method = RequestMethod.POST)
    public ReservationDto createReservation(@PathVariable Long meetingRoomId, @Valid @RequestBody ReservationDto reservationDto) {
        System.out.println("ReservationController - createReservation() - reservationDto: \n" + reservationDto
                .toString() + "\nmeetingRoomId : " + meetingRoomId);
        return reservationService.createReservation(meetingRoomId, reservationDto);
        /*
        {
    "meetingRoomId" : 1,
    "assignYear" : 1111,
    "assignMonth": 11,
    "assignDay" : 11,
    "assignHour" : 11,
    "totalUsingHours" : 1,
    "assignUserName" : "test",
    "assignUserPhoneNumber" : "111-111",
    "passWord" :
            }*/
    }

    @RequestMapping(value = "/reservations", method = RequestMethod.GET)
    public HashMap<Long, ArrayList<GetResponseReservationDto>> findAllReservation() throws NoReservationExistsException {
        return reservationService.findAllReservation();
    }

    @RequestMapping(value = "/reservations/{meetingRoomId}", method = RequestMethod.GET)
    public ArrayList<GetResponseReservationDto> findReservationByMeetingRoomId(@PathVariable Long meetingRoomId) {
        return reservationService.findReservationByMeetingRoomId(meetingRoomId);
    }

    //DELETE 메소드는 일반적으로 Request Body를 받지 않는다
    //근데 URI에 passWord 다 보이는데 POST 요청으로 passWord는 바디에 담아서 외부에 보이지 않게 해야 할 거 같다.
    //Body로 입력받을 때 입력해야 하는 값이 너무 난해하다 -> 1111-11-11T12:00:05 이렇게 형식 맞춰서 입력해야 했음,, -> 그냥 정수로 입력받고 Dto로 변환
    @RequestMapping(value = "/reservation/{meetingRoomIdForRemove}", method = RequestMethod.POST)
    public void deleteReservation(@PathVariable Long meetingRoomIdForRemove, @Valid @RequestBody RemoveReservationDto removeReservationDto
    ) throws NoReservationExistsException {
        reservationService.deleteReservation(meetingRoomIdForRemove, removeReservationDto);
    }
    /*
    http://localhost:8080/reservations/3?assignDateTime=1111-11-11T12:00:00&assignName=test&passWord=test2
    http://localhost:8080/reservations/3?assignDateTime=1111-11-11T11:00:00&assignName=test4&passWord=test4
     */
}
