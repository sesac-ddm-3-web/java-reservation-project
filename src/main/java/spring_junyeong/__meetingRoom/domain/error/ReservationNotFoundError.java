package spring_junyeong.__meetingRoom.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReservationNotFoundError extends ReservationError{
    private static final int DEFAULT_STATUS = 404;

    public ReservationNotFoundError(String message) {
        super(message, DEFAULT_STATUS);
    }

    public ReservationNotFoundError(String message, int status) {
        super(message, status);
    }

}
