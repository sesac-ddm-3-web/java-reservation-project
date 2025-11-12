package SpringMiniProject.SeSAC.MeetingRoom.Management.Application;

import SpringMiniProject.SeSAC.MeetingRoom.Management.Domain.NoReservationExistsException;
import SpringMiniProject.SeSAC.MeetingRoom.Management.Domain.RemoveReservation;
import SpringMiniProject.SeSAC.MeetingRoom.Management.Domain.Reservation;
import SpringMiniProject.SeSAC.MeetingRoom.Management.Infrastructure.ListReservationRepository;
import SpringMiniProject.SeSAC.MeetingRoom.Management.Presentation.GetResponseReservationDto;
import SpringMiniProject.SeSAC.MeetingRoom.Management.Presentation.RemoveReservationDto;
import SpringMiniProject.SeSAC.MeetingRoom.Management.Presentation.ReservationDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ReservationService {

    private ModelMapper modelMapper;
    private ListReservationRepository listReservationRepository;
    private ValidationService validationService;

    @Autowired
    public ReservationService(ModelMapper modelMapper,
                              ListReservationRepository listReservationRepository,
                              ValidationService validationService
    ) {
        this.modelMapper = modelMapper;
        this.listReservationRepository = listReservationRepository;
        this.validationService = validationService;
    }

    public ReservationDto createReservation(Long meetingRoomId, ReservationDto reservationDto) {
        System.out.println("ReservationService - createReservation() - reservationDto: \n"+reservationDto
                .toString());
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);
        System.out.println("ReservationService - createReservation() - reservation : \n"+reservation.toString());
        validationService.checkValid(reservation);
        Reservation savedReservation = listReservationRepository.add(meetingRoomId, reservation);
        ReservationDto savedReservationDto = modelMapper.map(savedReservation, ReservationDto.class);
        System.out.println("ReservationService - createReservation() - savedReservationDto: \n"+savedReservationDto.toString());

        return savedReservationDto;
    }

    public ArrayList<GetResponseReservationDto> findReservationByMeetingRoomId(Long meetingRoomId) {
        return listReservationRepository.findReservationDtoListByMeetingRoomId(meetingRoomId);
    }

    public HashMap<Long, ArrayList<GetResponseReservationDto>> findAllReservation() throws NoReservationExistsException {
        return listReservationRepository.findAllReservations();
    }

    public void deleteReservation(Long meetingRoomIdForRemove, RemoveReservationDto removeReservationDto) throws NoReservationExistsException {

        RemoveReservation removeReservation = modelMapper.map(removeReservationDto, RemoveReservation.class);
        listReservationRepository.removeReservationBy(meetingRoomIdForRemove, removeReservation);
        /*
        http://localhost:8080/reservations/3?assignDateTime=1111-11-11T12:00:00&assignName=test&passWord=test2
         */
    }
}

