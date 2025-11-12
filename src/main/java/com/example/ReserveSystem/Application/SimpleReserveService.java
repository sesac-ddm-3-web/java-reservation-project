package com.example.ReserveSystem.Application;


import com.example.ReserveSystem.Domain.MeetingRoom;
import com.example.ReserveSystem.Domain.PasswordIsNotSame;
import com.example.ReserveSystem.Domain.Reservation;
import com.example.ReserveSystem.Domain.ReservationConflictException;
import com.example.ReserveSystem.InfraStructure.ListMeetingRoomRepository;
import com.example.ReserveSystem.InfraStructure.ListReserveRepository;
import com.example.ReserveSystem.Presentation.PasswordDto;
import com.example.ReserveSystem.Presentation.ReservationDto;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SimpleReserveService {

    // 의존성 주입
    private ListMeetingRoomRepository listMeetingRoomRepository;
    private ListReserveRepository listReserveRepository;
    private ModelMapper modelMapepr;
    private ValidationService validationService;

    @Autowired
    SimpleReserveService(ListMeetingRoomRepository listMeetingRoomRepository, ListReserveRepository listReserveRepository, ModelMapper modelMapepr, ValidationService validationService){
        this.listMeetingRoomRepository = listMeetingRoomRepository;
        this.listReserveRepository = listReserveRepository;
        this.modelMapepr = modelMapepr;
        this.validationService = validationService;
    }


    @PostConstruct
    public void createMeetingRooms(){
        listMeetingRoomRepository.createMeetingRoom();
    }

    // 회의실 목록 조회하기
    public List<MeetingRoom> findMeetingRoomByAll(){
        return listMeetingRoomRepository.findByAll();
    }


    // 예약 생성하기
    public ReservationDto createReservation(ReservationDto reservationDto){
        Integer roomId = reservationDto.getRoomId();
        // 1. reservationDto -> reservation
        Reservation newReservation = modelMapepr.map(reservationDto, Reservation.class);

        // 유효성 검사
        validationService.checkValid(newReservation);
        // 2. 기존의 예약시간대와 겹치는지 확인
        boolean isOverlapping = listReserveRepository.findByRoomId(roomId).stream()
                .anyMatch(existing -> existing.isTimeOverlapping(newReservation)
                );

        if(isOverlapping){
           throw new ReservationConflictException("예약시간대가 겹칩니다.");
        }


        Reservation savedReservation = listReserveRepository.createReservation(newReservation);

        // reservation -> reservationDto
        ReservationDto savedReservationDto = modelMapepr.map(savedReservation, ReservationDto.class);
        return savedReservationDto;

    }

    // 특정 회의실의 모든 예약 현황을 조회하기
    public List<ReservationDto> findReservationsByRoomId(Integer roomId){
        return listReserveRepository.findByRoomId(roomId).stream()
                .map(reservation -> modelMapepr.map(reservation, ReservationDto.class))
                .toList();
    }

    // 특정회의실 삭제하기
    public void delete(Integer id, PasswordDto passwordDto){
        String password = passwordDto.getPassword();
        boolean isPasswordSame = listReserveRepository.findById(id).checkPassword(password);
        if(!isPasswordSame){
          throw new PasswordIsNotSame("password가 일치하지 않습니다.");
        }
        listReserveRepository.delete(id,password);
    }
}
