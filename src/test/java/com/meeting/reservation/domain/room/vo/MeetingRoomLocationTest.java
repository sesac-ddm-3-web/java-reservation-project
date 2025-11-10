package com.meeting.reservation.domain.room.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MeetingRoomLocationTest {

    @Test
    void 회의실_위치를_생성한다() {
        // when
        MeetingRoomLocation actual = MeetingRoomLocation.create(1, 1);

        // then
        assertAll(
                () -> assertThat(actual).isInstanceOf(MeetingRoomLocation.class),
                () -> assertThat(actual.getFloor()).isOne(),
                () -> assertThat(actual.getRoomNumber()).isOne()
        );
    }

    @ParameterizedTest(name = "{0} 일 때 생성할 수 없다.")
    @ValueSource(ints = {0, -1})
    void 회의실_방_번호는_양수여야_한다(int roomNumber) {
        // when & then
        assertThatThrownBy(() -> MeetingRoomLocation.create(1, roomNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회의실 방 번호는 양수여야 합니다.");
    }

    @Test
    void 회의실_위치는_층수와_방_번호가_동일하면_동등하다() {
        // given
        MeetingRoomLocation first = MeetingRoomLocation.create(1, 1);
        MeetingRoomLocation second = MeetingRoomLocation.create(1, 1);

        // when
        boolean actual = first.equals(second);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 회의실_위치는_층수가_동일해도_방_번호가_다르다면_동등하지_않다() {
        // given
        MeetingRoomLocation first = MeetingRoomLocation.create(1, 1);
        MeetingRoomLocation second = MeetingRoomLocation.create(1, 2);

        // when
        boolean actual = first.equals(second);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void 회의실_위치는_방_번호가_동일해도_층수가_다르다면_동등하지_않다() {
        // given
        MeetingRoomLocation first = MeetingRoomLocation.create(1, 1);
        MeetingRoomLocation second = MeetingRoomLocation.create(2, 1);

        // when
        boolean actual = first.equals(second);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void 해당_회의실_위치가_지정한_층수와_방_번호가_맞는지_확인한다() {
        // given
        MeetingRoomLocation meetingRoomLocation = MeetingRoomLocation.create(1, 1);

        // when
        boolean actual = meetingRoomLocation.isEqualLocation(1, 1);

        // then
        assertThat(actual).isTrue();
    }
}
