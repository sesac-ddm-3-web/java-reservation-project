package SpringMiniProject.SeSAC.MeetingRoom.Management.Domain;

import java.time.LocalDateTime;

public class RemoveReservation {
    private LocalDateTime assignDateTime;
//    private LocalDateTime dueDateTime;

    private int assignYear;
    private int assignMonth;
    private int assignDay;
    private int assignHour;
    private String assignUserName;
    private String assignPassWord;

    public void setAssignDateTime() {
        this.assignDateTime = LocalDateTime.of(assignYear, assignMonth, assignDay, assignHour, 0);
    }

    public boolean checkAuthorization(LocalDateTime startDatetime, String assignPassWord, String assignUserName){
        return(this.assignDateTime.equals(startDatetime)
                && this.assignPassWord.equals(assignPassWord)
                && this.assignUserName.equals(assignUserName)
        );
    }
}
