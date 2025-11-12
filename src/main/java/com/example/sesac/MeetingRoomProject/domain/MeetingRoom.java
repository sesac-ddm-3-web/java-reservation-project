package com.example.sesac.MeetingRoomProject.domain;

import java.util.List;

public class MeetingRoom {
    private Long id;
    private String name;

    public MeetingRoom(Long id,String name) {
        this.id = id;
        this.name = name;
    }

    public boolean sameId(Long id){
        return this.id.equals(id);
    }

    public Long getId(){
        return id;
    }

}
