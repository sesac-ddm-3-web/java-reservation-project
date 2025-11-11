package spring_junyeong.__meetingRoom.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PasswordMismatchError extends ReservationError{
    private static final int DEFAULT_STATUS = 403;

    public PasswordMismatchError(String message) {
        super(message, DEFAULT_STATUS);
    }

    public PasswordMismatchError(String message, int status) {
        super(message, status);
    }
}
