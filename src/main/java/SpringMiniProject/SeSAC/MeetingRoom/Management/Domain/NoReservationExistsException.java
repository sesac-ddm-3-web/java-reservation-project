package SpringMiniProject.SeSAC.MeetingRoom.Management.Domain;

public class NoReservationExistsException extends RuntimeException {
    public NoReservationExistsException(String message) {
        super(message);
    }
}

