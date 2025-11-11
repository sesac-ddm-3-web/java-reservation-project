package spring_junyeong.__meetingRoom.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_junyeong.__meetingRoom.domain.*;
import spring_junyeong.__meetingRoom.domain.dto.ReservationDto;
import spring_junyeong.__meetingRoom.domain.dto.ReservationResponseDto;
import spring_junyeong.__meetingRoom.domain.error.*;
import spring_junyeong.__meetingRoom.repository.MeetingRoomRepository;
import spring_junyeong.__meetingRoom.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    ReservationRepository reservationRepository;
    MeetingRoomRepository meetingRoomRepository;
    ModelMapper modelMapper;

    @Autowired
    ReservationService(ReservationRepository reservationRepository, MeetingRoomRepository meetingRoomRepository, ModelMapper modelMapper){
        this.reservationRepository = reservationRepository;
        this.meetingRoomRepository = meetingRoomRepository;
        this.modelMapper = modelMapper;
    }

    // Dto와 Domain 객체의 구조가 달라 Convert 함수를 정의해주어야 함
    public Reservation convertToEntity(ReservationDto dto, MeetingRoom room){
        Reservation reservation = modelMapper.map(dto, Reservation.class);

        reservation.setRoom(room);

        User user = new User(
                dto.getUserName(),
                dto.getPassword(),
                dto.getPhoneNumber()
        );
        reservation.setUser(user);

        return reservation;
    }

    // Reservation Entity를 responseDto로 변환하는 코드
    public ReservationResponseDto convertToDto(Reservation reservation) {
        ReservationResponseDto dto = modelMapper.map(reservation, ReservationResponseDto.class);

        if (reservation.getRoom() != null) {
            dto.setMeetingRoomId(reservation.getRoom().getId());
            dto.setRoomName(reservation.getRoom().getName());
        }

        if (reservation.getReservationUser() != null) {
            User user = reservation.getReservationUser();
            dto.setUserName(user.getName());
            dto.setPhoneNumber(user.getPhoneNumber());
        }

        return dto;
    }

    public ReservationResponseDto createReservation(ReservationDto reservationDto, Long roomId){
        MeetingRoom room = meetingRoomRepository.findById(roomId)
                .orElseThrow(() -> new MeetingRoomNotFoundError("존재하지 않는 회의실입니다. 회의실 번호를 다시 확인해주세요.", 404));

        validateMaxCapacity(reservationDto, room);

        Reservation newReservation = convertToEntity(reservationDto, room);

        validateReservationTimeOverwrap(newReservation, room.getId());

        Reservation reservationEntity = reservationRepository.add(newReservation);
        return convertToDto(reservationEntity);

    }

    public List<ReservationResponseDto> getReservationsByRoomId(Long roomId){
        List<Reservation> reservationEntityList =  reservationRepository.getReservationsByRoomId(roomId);

        return reservationEntityList.stream().map(this::convertToDto).toList();
    }

    public void removeReservation(Long reservationId, String password){
        Reservation reservation = reservationRepository.getReservationById(reservationId);
        User reservationUser = reservation.getReservationUser();

        if(reservationUser.isPasswordRight(password)) {
            reservationRepository.remove(reservationId);
        }else {
            throw new PasswordMismatchError("비밀번호가 일치하지 않습니다.", 403);
        }

    }

    public void validateMaxCapacity(ReservationDto reservationDto, MeetingRoom room){
        int participantsCount = reservationDto.getParticipantCount();
        int roomMaxCapacity = room.getMaxCapacity();

        if(roomMaxCapacity < participantsCount) throw new CapacityExceededError("참가 인원이 회의실에 수용 인원을 초과했습니다.", 400);
    }

    public void validateReservationTimeOverwrap(Reservation newReservation, Long roomId){
        List<Reservation> existingReservations = reservationRepository.getReservationsByRoomId(roomId);

        boolean isOverwrap = existingReservations.stream()
                .anyMatch(existing -> existing.isReserveOverwrap(newReservation));

        if(isOverwrap) throw new TimeOverlapError("이미 예약된 시간입니다.", 400);
    }
}
