package spring_junyeong.__meetingRoom.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TimeOverlapError extends ReservationError{
    private static final int DEFAULT_STATUS = 400;

    public TimeOverlapError(String message) {
        super(message, DEFAULT_STATUS);
    }

    public TimeOverlapError(String message, int status) {
        super(message, status);
    }
}
