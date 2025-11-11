package spring_junyeong.__meetingRoom.domain.error;


public class ReservationError extends RuntimeException{
    private final int statusCode;

    public ReservationError(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
