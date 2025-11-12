package com.example.ReserveSystem.InfraStructure;


import com.example.ReserveSystem.Domain.MeetingRoom;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;


@Repository
public class ListMeetingRoomRepository {

    ConcurrentHashMap<Integer, AtomicBoolean> meetingRooms = new ConcurrentHashMap<>();



    // 1. 회의실 초기화 및 생성
    public void createMeetingRoom(){
        for(int roomNum = 100; roomNum <= 105; roomNum++){
            meetingRooms.put(roomNum, new AtomicBoolean(false));
        }
    }

    // 2. 전체 회의실 목록 조회하기
    public List<MeetingRoom> findByAll(){

        return meetingRooms.keySet().stream()
                .sorted()

                .map(roomNum -> {
                    AtomicBoolean status = meetingRooms.get(roomNum);
                    Boolean isReserved = status.get();
                    return new MeetingRoom(roomNum, isReserved);

                })
                .toList();
    }

}
