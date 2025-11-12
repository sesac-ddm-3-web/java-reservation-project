package SpringMiniProject.SeSAC.MeetingRoom.Management.Infrastructure;

import SpringMiniProject.SeSAC.MeetingRoom.Management.Domain.NoReservationExistsException;
import SpringMiniProject.SeSAC.MeetingRoom.Management.Domain.RemoveReservation;
import SpringMiniProject.SeSAC.MeetingRoom.Management.Domain.Reservation;
import SpringMiniProject.SeSAC.MeetingRoom.Management.Domain.EmptyReservationListException;
import SpringMiniProject.SeSAC.MeetingRoom.Management.Presentation.GetResponseReservationDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.*;

@Repository
public class ListReservationRepository {

    private final DispatcherServlet dispatcherServlet;
    private HashMap<Long, ArrayList<Reservation>> reservationInfo = new HashMap<>();
    ModelMapper modelMapper;

    @Autowired
    public ListReservationRepository(ModelMapper modelMapper, DispatcherServlet dispatcherServlet) {
        this.modelMapper = modelMapper;
        this.dispatcherServlet = dispatcherServlet;
    }

    public Reservation add(Long meetingRoomId, Reservation newReservation) {
        //여기서 충돌 감지 후 예외 처리
        newReservation.setAssignDateTime();
        newReservation.setDueDateTime();
        newReservation.setMeetingRoomId(meetingRoomId);
        System.out.println("ListReservationRepository - add() : \n"+newReservation);
        //meetingRoomId의 Reservations 리스트 불러오고 없으면 새로 생성
        ArrayList<Reservation> reservations =
                reservationInfo.computeIfAbsent(meetingRoomId, k -> new ArrayList<>());

        //리스트에 값이 채워진 경우 충돌 검사 진행
        if (!reservations.isEmpty()) {
            reservations.forEach(existing -> existing.checkReservationCollision(newReservation));
        }

        reservations.add(newReservation);
        return newReservation;
    }


    //MeetingRoomId로 해당 Id의 회의실 예약 정보를 ArrayList<GetResponseReservationDto> 로 바로 변환
    public ArrayList<GetResponseReservationDto> findReservationDtoListByMeetingRoomId(Long meetingRoomId) throws EmptyReservationListException {
        //todo
        //아직 List<Reservation>이 존재하지 않는 MeetingRoomId 를 조회하는 경우 예외 처리
        //전역 예외 핸들러로 처리

        ArrayList<GetResponseReservationDto> getResponseReservationDtoArrayList = new ArrayList<>();
        findReservationsByMeetingRoomId(meetingRoomId)
                .forEach(reservation -> getResponseReservationDtoArrayList.add(modelMapper.map(reservation, GetResponseReservationDto.class)));;
        return getResponseReservationDtoArrayList;
    }

    //List가 비어있을 때 예외처리를 해야 할지, 빈 배열을 넘겨야 할지 어떤게 실제 서비스에 더 절절한지 모르겠다
    // -> 일단은 직접 아무것도 없음에 대한 예외를 던지도록 설계
    public HashMap<Long, ArrayList<GetResponseReservationDto>> findAllReservations() throws NoReservationExistsException {
        boolean allEmpty = reservationInfo.values().stream().allMatch(List::isEmpty);

        if (reservationInfo.isEmpty() || allEmpty) {
            throw new NoReservationExistsException("예약이 존재하지 않습니다.");
        }

        HashMap<Long, ArrayList<GetResponseReservationDto>> dtoMap = new HashMap<>();

        reservationInfo.forEach((meetingRoomId, reservations) -> {
            if (reservations != null && !reservations.isEmpty()) {
                reservations.forEach(reservation -> {
                    GetResponseReservationDto dto = modelMapper.map(reservation, GetResponseReservationDto.class);
                    dtoMap.computeIfAbsent(meetingRoomId, k -> new ArrayList<>()).add(dto);
                });
            }
        });

        return dtoMap;
    }

    public void removeReservationBy(Long meetingRoomIdForRemove, RemoveReservation removeReservation) throws NoReservationExistsException {
//        System.out.println("[삭제 전]\n"+findAllReservations().toString());
        findReservationsByMeetingRoomId(meetingRoomIdForRemove)
                .removeIf(reservation -> reservation.checkAuthorization(removeReservation));
//        System.out.println("[삭제 후]\n"+findAllReservations().toString());

    }

    //MeetingRoomId로 해당 MeetingRoomId의 예약 정보 리스트 가져오는 메서드 추출 -> 조회, 삭제 재사용
    private ArrayList<Reservation> findReservationsByMeetingRoomId(Long meetingRoomId) {
        if(!reservationInfo.containsKey(meetingRoomId)||
                Optional.ofNullable(reservationInfo.get(meetingRoomId))
                        .orElse(new ArrayList<>())
                        .isEmpty()
        ){
            throw new EmptyReservationListException(meetingRoomId+"번 회의실에 예약이 존재하지 않습니다.");
        }
        else{
            return reservationInfo.get(meetingRoomId);
        }
        //뭔가 각 CRUD등 각 기능들마다 공통적으로 수행하는 (ex.Id로 조회) 작업들은 작업 과정+예외 다 처리해 둬서 기능을 구현할 때는 그런 부분 신경 안 쓰게 하는 게 편리할 거 같다.
    }

    //todo
    //시간 지나면 자동으로 기간 지난 예약을 삭제해서 Map 크기 줄여야겠다
}