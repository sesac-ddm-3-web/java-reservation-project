package com.meeting.reservation.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.meeting.reservation.domain.room.MeetingRoom;
import com.meeting.reservation.domain.room.MeetingRooms;
import com.meeting.reservation.domain.room.vo.MeetingRoomLocation;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class InMemoryMeetingRoomRepositoryTest {

    @Test
    void 미리_초기화된_회의실을_가진_레포지토리를_생성한다() {
        // given
        InMemoryMeetingRoomRepository repository = new InMemoryMeetingRoomRepository();

        // when
        MeetingRooms meetingRooms = repository.findAll();
        List<MeetingRoom> actual = meetingRooms.getMeetingRooms();

        // then
        assertAll(
                () -> assertThat(actual.get(0).getName()).isEqualTo("회의실 101"),
                () -> assertThat(actual.get(0).getLocation()).isEqualTo(MeetingRoomLocation.create(1, 1)),
                () -> assertThat(actual.get(0).getId().getValue()).isEqualTo(1L),

                () -> assertThat(actual.get(1).getName()).isEqualTo("회의실 102"),
                () -> assertThat(actual.get(1).getLocation()).isEqualTo(MeetingRoomLocation.create(1, 2)),
                () -> assertThat(actual.get(1).getId().getValue()).isEqualTo(2L),

                () -> assertThat(actual.get(2).getName()).isEqualTo("회의실 103"),
                () -> assertThat(actual.get(2).getLocation()).isEqualTo(MeetingRoomLocation.create(1, 3)),
                () -> assertThat(actual.get(2).getId().getValue()).isEqualTo(3L),

                () -> assertThat(actual.get(3).getName()).isEqualTo("회의실 201"),
                () -> assertThat(actual.get(3).getLocation()).isEqualTo(MeetingRoomLocation.create(2, 1)),
                () -> assertThat(actual.get(3).getId().getValue()).isEqualTo(4L),

                () -> assertThat(actual.get(4).getName()).isEqualTo("회의실 202"),
                () -> assertThat(actual.get(4).getLocation()).isEqualTo(MeetingRoomLocation.create(2, 2)),
                () -> assertThat(actual.get(4).getId().getValue()).isEqualTo(5L),

                () -> assertThat(actual.get(5).getName()).isEqualTo("회의실 301"),
                () -> assertThat(actual.get(5).getLocation()).isEqualTo(MeetingRoomLocation.create(3, 1)),
                () -> assertThat(actual.get(5).getId().getValue()).isEqualTo(6L),

                () -> assertThat(actual.get(6).getName()).isEqualTo("지하 1층 1번 회의실"),
                () -> assertThat(actual.get(6).getLocation()).isEqualTo(MeetingRoomLocation.create(-1, 1)),
                () -> assertThat(actual.get(6).getId().getValue()).isEqualTo(7L)
        );
    }

}
