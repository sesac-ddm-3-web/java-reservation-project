package spring_junyeong.__meetingRoom.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MeetingRoomNotFoundError extends ReservationError{
    private static final int DEFAULT_STATUS = 404;

    public MeetingRoomNotFoundError(String message) {
        super(message, DEFAULT_STATUS);
    }

    public MeetingRoomNotFoundError(String message, int status) {
        super(message, status);
    }

}
