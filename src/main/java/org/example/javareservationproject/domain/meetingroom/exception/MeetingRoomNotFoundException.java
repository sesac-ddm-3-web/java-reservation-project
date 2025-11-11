package org.example.javareservationproject.domain.meetingroom.exception;

import org.example.javareservationproject.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class MeetingRoomNotFoundException extends BusinessException {

    public MeetingRoomNotFoundException() {
        super(HttpStatus.NOT_FOUND, "입력된 ID에 해당하는 회의실이 존재하지 않습니다.");
    }
}
