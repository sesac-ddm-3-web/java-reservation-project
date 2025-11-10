package com.meeting.reservation.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.meeting.reservation.domain.reservation.Reservation;
import com.meeting.reservation.domain.reservation.Reservations;
import com.meeting.reservation.domain.reservation.vo.Organizer;
import com.meeting.reservation.persistence.InMemoryReservationRepository.MeetingRoomNotFoundException;
import com.meeting.reservation.persistence.InMemoryReservationRepository.ReservationNotFoundException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class InMemoryReservationRepositoryTest {

    @Test
    void 예약을_저장한다() {
        // given
        InMemoryReservationRepository repository = new InMemoryReservationRepository();
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1L);
        Reservation reservation = Reservation.create(organizer, startTime, endTime);

        // when
        Reservation actual = repository.save(1L, reservation);

        // then
        assertThat(actual.getId().getValue()).isEqualTo(1L);
    }

    @Test
    void 예약을_저장할_때마다_ID가_자동으로_증가한다() {
        // given
        InMemoryReservationRepository repository = new InMemoryReservationRepository();
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1L);
        Reservation reservation1 = Reservation.create(organizer, startTime, endTime);
        Reservation reservation2 = Reservation.create(organizer, startTime.plusDays(1L), endTime.plusDays(1L));

        // when
        Reservation saved1 = repository.save(1L, reservation1);
        Reservation saved2 = repository.save(1L, reservation2);

        // then
        assertAll(
                () -> assertThat(saved1.getId().getValue()).isEqualTo(1L),
                () -> assertThat(saved2.getId().getValue()).isEqualTo(2L)
        );
    }

    @Test
    void 예약을_삭제한다() {
        // given
        InMemoryReservationRepository repository = new InMemoryReservationRepository();
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1L);
        Reservation reservation = Reservation.create(organizer, startTime, endTime);
        Reservation saved = repository.save(1L, reservation);

        // when
        repository.delete(1L, saved.getId().getValue());
        Reservations actual = repository.findAll(1L);

        // then
        assertThat(actual.getReservations()).isEmpty();
    }

    @Test
    void 존재하지_않는_회의실의_예약을_삭제하려_하면_예외가_발생한다() {
        // given
        InMemoryReservationRepository repository = new InMemoryReservationRepository();

        // when & then
        assertThatThrownBy(() -> repository.delete(-999L, 1L))
                .isInstanceOf(MeetingRoomNotFoundException.class)
                .hasMessage("지정한 회의실 ID에 대한 예약을 찾지 못했습니다.");
    }

    @Test
    void 존재하지_않는_예약_ID를_삭제하려_하면_예외가_발생한다() {
        // given
        InMemoryReservationRepository repository = new InMemoryReservationRepository();
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1L);
        Reservation reservation = Reservation.create(organizer, startTime, endTime);
        repository.save(1L, reservation);

        // when & then
        assertThatThrownBy(() -> repository.delete(1L, -999L))
                .isInstanceOf(ReservationNotFoundException.class)
                .hasMessage("지정한 ID에 대한 예약을 찾지 못했습니다.");
    }

    @Test
    void 예약이_없는_회의실의_모든_예약을_조회하면_빈_목록을_반환한다() {
        // given
        InMemoryReservationRepository repository = new InMemoryReservationRepository();

        // when
        Reservations actual = repository.findAll(1L);

        // then
        assertThat(actual.getReservations()).isEmpty();
    }

    @Test
    void 회의실의_모든_예약을_조회한다() {
        // given
        InMemoryReservationRepository repository = new InMemoryReservationRepository();
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1L);
        Reservation reservation1 = Reservation.create(organizer, startTime, endTime);
        Reservation reservation2 = Reservation.create(organizer, startTime.plusDays(1L), endTime.plusDays(1L));
        repository.save(1L, reservation1);
        repository.save(1L, reservation2);

        // when
        Reservations actual = repository.findAll(1L);

        // then
        assertThat(actual.getReservations()).hasSize(2);
    }

    @Test
    void 회의실별로_독립적인_예약_목록을_관리한다() {
        // given
        InMemoryReservationRepository repository = new InMemoryReservationRepository();
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1L);
        Reservation reservation1 = Reservation.create(organizer, startTime, endTime);
        Reservation reservation2 = Reservation.create(organizer, startTime.plusDays(1L), endTime.plusDays(1L));
        Reservation reservation3 = Reservation.create(organizer, startTime.plusDays(2L), endTime.plusDays(2L));

        // when
        repository.save(1L, reservation1);
        repository.save(1L, reservation2);
        repository.save(2L, reservation3);

        Reservations room1Reservations = repository.findAll(1L);
        Reservations room2Reservations = repository.findAll(2L);

        // then
        assertAll(
                () -> assertThat(room1Reservations.getReservations()).hasSize(2),
                () -> assertThat(room2Reservations.getReservations()).hasSize(1)
        );
    }
}
