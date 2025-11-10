package com.meeting.reservation.domain.room;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.meeting.reservation.domain.room.vo.MeetingRoomId;
import com.meeting.reservation.domain.room.vo.MeetingRoomLocation;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MeetingRoomTest {

    @Test
    void 회의실을_생성한다() {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);

        // when
        MeetingRoom actual = MeetingRoom.create("학습실1", location);

        // then
        assertAll(
                () -> assertThat(actual).isInstanceOf(MeetingRoom.class),
                () -> assertThat(actual.getId()).isSameAs(MeetingRoomId.EMPTY_MEETING_ROOM_ID),
                () -> assertThat(actual.getName()).isEqualTo("학습실1")
        );
    }

    @ParameterizedTest(name = "{0}일 때 생성할 수 없다.")
    @NullAndEmptySource
    void 회의실_이름이_비어서는_안_된다(String name) {
        // when & then
        assertThatThrownBy(() -> MeetingRoom.create(name, MeetingRoomLocation.create(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회의실 이름은 비어 있을 수 없습니다.");
    }

    @Test
    void 회의실_위치는_비어서는_안_된다() {
        // when & then
        assertThatThrownBy(() -> MeetingRoom.create("학습실1", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회의실 위치는 비어 있을 수 없습니다.");
    }

    @Test
    void 유효한_회의실_ID를_초기화한다() {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);
        MeetingRoom meetingRoom = MeetingRoom.create("학습실1", location);

        // when
        MeetingRoom actual = meetingRoom.withAssignedId(1L);

        // then
        assertThat(actual.getId().getValue()).isEqualTo(1L);
    }

    @ParameterizedTest(name = "{0} 일 때 생성할 수 없다.")
    @ValueSource(longs = {0L, -1L})
    void 유효하지_않은_회의실_ID는_초기화할_수_없다(Long id) {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);
        MeetingRoom meetingRoom = MeetingRoom.create("학습실1", location);

        // when & then
        assertThatThrownBy(() -> meetingRoom.withAssignedId(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회의실 ID는 양수여야 합니다.");
    }

    @Test
    void 회의실_위치를_변경한다() {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);
        MeetingRoom meetingRoom = MeetingRoom.create("학습실1", location);

        // when
        MeetingRoom actual = meetingRoom.moveLocation(MeetingRoomLocation.create(2, 2));

        // then
        assertThat(actual.getLocation()).isEqualTo(MeetingRoomLocation.create(2, 2));
    }

    @Test
    void 회의실_이름을_변경한다() {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);
        MeetingRoom meetingRoom = MeetingRoom.create("학습실1", location);

        // when
        MeetingRoom actual = meetingRoom.changeName("집중학습실1");

        // then
        assertThat(actual.getName()).isEqualTo("집중학습실1");
    }

    @ParameterizedTest(name = "{0}일 때 생성할 수 없다.")
    @NullAndEmptySource
    void 유효하지_않은_회의실_이름으로는_변경할_수_없다(String name) {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);
        MeetingRoom meetingRoom = MeetingRoom.create("학습실1", location);

        // when & then
        assertThatThrownBy(() -> meetingRoom.changeName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회의실 이름은 비어 있을 수 없습니다.");
    }

    @Test
    void 회의실_ID가_동등한지_확인한다() {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);
        MeetingRoom meetingRoom = MeetingRoom.create("학습실1", location)
                                             .withAssignedId(1L);

        // when
        boolean actual = meetingRoom.isEqualId(1L);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 회의실_이름이_동등한지_확인한다() {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);
        MeetingRoom meetingRoom = MeetingRoom.create("학습실1", location);

        // when
        boolean actual = meetingRoom.isEqualName("학습실1");

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 회의실_위치가_동등한지_확인한다() {
        // given
        MeetingRoomLocation location = MeetingRoomLocation.create(1, 1);
        MeetingRoom meetingRoom = MeetingRoom.create("학습실1", location);

        // when
        boolean actual = meetingRoom.isEqualLocation(1, 1);

        // then
        assertThat(actual).isTrue();
    }
}
