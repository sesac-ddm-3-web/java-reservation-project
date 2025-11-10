package com.meeting.reservation.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.meeting.reservation.domain.reservation.vo.Organizer;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTest {

    @Test
    void 예약을_생성한다() {
        // given
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusSeconds(1L);

        // when
        Reservation actual = Reservation.create(organizer, startTime, endTime);

        // then
        assertThat(actual).isInstanceOf(Reservation.class);
    }

    @Test
    void 예약자_정보가_있어야_한다() {
        // given
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusSeconds(1L);

        // when & then
        assertThatThrownBy(() -> Reservation.create(null, startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 정보는 비어 있을 수 없습니다.");
    }

    @Test
    void 예약_시간_정보가_있어야_한다() {
        // given
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");

        // when & then
        assertThatThrownBy(() -> Reservation.create(organizer, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간 정보는 비어 있을 수 없습니다.");
    }

    @Test
    void 예약_시작_시간은_예약_종료_시간보다_앞서야_한다() {
        // given
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.plusSeconds(1L);

        // when & then
        assertThatThrownBy(() -> Reservation.create(organizer, startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시작 시간은 예약 종료 시간보다 이전이어야 합니다.");
    }

    @Test
    void 예약끼리_시간이_겹치는지_확인한다() {
        // given
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusSeconds(1L);

        Reservation first = Reservation.create(organizer, startTime, endTime);
        Reservation second = Reservation.create(organizer, startTime, endTime);

        // when
        boolean actual = first.overlapTime(second);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 유효한_예약_ID를_초기화한다() {
        // given
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusSeconds(1L);
        Reservation reservation = Reservation.create(organizer, startTime, endTime);

        // when
        Reservation actual = reservation.withAssignedId(1L);

        // then
        assertThat(actual.getId().getValue()).isEqualTo(1L);
    }

    @ParameterizedTest(name = "{0} 일 때 생성할 수 없다.")
    @ValueSource(longs = {0L, -1L})
    void 유효하지_않은_예약_ID는_초기화할_수_없다(Long id) {
        // given
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusSeconds(1L);
        Reservation reservation = Reservation.create(organizer, startTime, endTime);

        // when & then
        assertThatThrownBy(() -> reservation.withAssignedId(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 ID는 양수여야 합니다.");
    }

    @Test
    void 비밀번호가_일치하는지_확인한다() {
        // given
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusSeconds(1L);
        Reservation reservation = Reservation.create(organizer, startTime, endTime);

        // when
        boolean actual = reservation.matchPassword("1234");

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 예약_ID가_일치하는지_확인한다() {
        // given
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusSeconds(1L);
        Reservation reservation = Reservation.create(organizer, startTime, endTime)
                                             .withAssignedId(1L);

        // when
        boolean actual = reservation.isEqualId(1L);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 회의실을_사용했는지_확인한다() {
        // given
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusSeconds(1L);
        Reservation reservation = Reservation.create(organizer, startTime, endTime)
                                             .withAssignedId(1L);

        // when
        boolean actual = reservation.afterStartTime(LocalDateTime.now().plusDays(1L));

        // then
        assertThat(actual).isTrue();
    }
}
