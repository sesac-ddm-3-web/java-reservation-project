package com.meeting.reservation.domain.room;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.meeting.reservation.domain.room.MeetingRooms.MeetingRoomNotFoundException;
import com.meeting.reservation.domain.room.vo.MeetingRoomLocation;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MeetingRoomsTest {

    @Test
    void 회의실_목록을_생성한다() {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);
        MeetingRoom meetingRoom = MeetingRoom.create("학습실1", location)
                                             .withAssignedId(1L);

        // when
        MeetingRooms actual = MeetingRooms.create(List.of(meetingRoom));

        // then
        assertAll(
                () -> assertThat(actual).isInstanceOf(MeetingRooms.class),
                () -> assertThat(actual.getMeetingRooms()).hasSize(1)
        );
    }

    @Test
    void 회의실_ID로_회의실을_조회한다() {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);
        MeetingRoom meetingRoom = MeetingRoom.create("학습실1", location)
                                             .withAssignedId(1L);
        MeetingRooms meetingRooms = MeetingRooms.create(List.of(meetingRoom));

        // when
        MeetingRoom actual = meetingRooms.findMeetingRoom(1L);

        // then
        assertThat(actual.getId().getValue()).isEqualTo(1L);
    }

    @Test
    void 존재하지_않는_회의실_ID로_조회할_수_없다() {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);
        MeetingRoom meetingRoom = MeetingRoom.create("학습실1", location)
                                             .withAssignedId(1L);
        MeetingRooms meetingRooms = MeetingRooms.create(List.of(meetingRoom));

        // when & then
        assertThatThrownBy(() -> meetingRooms.findMeetingRoom(-999L))
                .isInstanceOf(MeetingRoomNotFoundException.class)
                .hasMessage("지정한 ID에 해당하는 회의실을 찾을 수 없습니다.");
    }

    @Test
    void 회의실_위치로_회의실을_조회한다() {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);
        MeetingRoom meetingRoom = MeetingRoom.create("학습실1", location)
                                             .withAssignedId(1L);
        MeetingRooms meetingRooms = MeetingRooms.create(List.of(meetingRoom));

        // when
        MeetingRoom actual = meetingRooms.findMeetingRoom(1, 1);

        // then
        assertThat(actual.getLocation()).isEqualTo(location);
    }

    @Test
    void 존재하지_않는_회의실_위치로_회의실을_조회할_수_없다() {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);
        MeetingRoom meetingRoom = MeetingRoom.create("학습실1", location)
                                             .withAssignedId(1L);
        MeetingRooms meetingRooms = MeetingRooms.create(List.of(meetingRoom));

        // when & then
        assertThatThrownBy(() -> meetingRooms.findMeetingRoom(1, 2))
                .isInstanceOf(MeetingRoomNotFoundException.class)
                .hasMessage("지정한 위치에 해당하는 회의실을 찾을 수 없습니다.");
    }

    @Test
    void 회의실_이름으로_회의실을_조회한다() {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);
        MeetingRoom meetingRoom = MeetingRoom.create("학습실1", location)
                                             .withAssignedId(1L);
        MeetingRooms meetingRooms = MeetingRooms.create(List.of(meetingRoom));

        // when
        MeetingRoom actual = meetingRooms.findMeetingRoom("학습실1");

        // then
        assertThat(actual.getName()).isEqualTo("학습실1");
    }

    @Test
    void 존재하지_않는_회의실_이름으로_회의실을_조회할_수_없다() {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);
        MeetingRoom meetingRoom = MeetingRoom.create("학습실1", location)
                                             .withAssignedId(1L);
        MeetingRooms meetingRooms = MeetingRooms.create(List.of(meetingRoom));

        // when & then
        assertThatThrownBy(() -> meetingRooms.findMeetingRoom("없는 이름"))
                .isInstanceOf(MeetingRoomNotFoundException.class)
                .hasMessage("지정한 이름에 해당하는 회의실을 찾을 수 없습니다.");
    }
}
