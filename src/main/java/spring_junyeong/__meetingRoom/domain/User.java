package spring_junyeong.__meetingRoom.domain;


import java.util.concurrent.atomic.AtomicLong;

public class User {
    private Long id;
    private String name;
    private String password;
    private String phoneNumber;
    private static final AtomicLong sequence = new AtomicLong(0L);


    public User(){}

    public User(String name, String password, String phoneNumber){
        this.id = sequence.incrementAndGet();
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isPasswordRight(String password) {
        return this.password.equals(password);
    }
}
