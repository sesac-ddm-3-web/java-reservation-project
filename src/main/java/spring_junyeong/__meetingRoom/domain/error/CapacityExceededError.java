package spring_junyeong.__meetingRoom.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CapacityExceededError extends ReservationError{
    private static final int DEFAULT_STATUS = 400;

    public CapacityExceededError(String message) {
        super(message, DEFAULT_STATUS);
    }

    public CapacityExceededError(String message, int status) {
        super(message, status);
    }

}
