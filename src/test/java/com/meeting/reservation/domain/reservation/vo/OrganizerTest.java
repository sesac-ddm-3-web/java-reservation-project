package com.meeting.reservation.domain.reservation.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrganizerTest {

    @Test
    void 예약자를_생성한다() {
        // when
        Organizer actual = Organizer.create("예약자", "010-1234-5678", "1234");

        // then
        assertThat(actual).isInstanceOf(Organizer.class);
    }

    @ParameterizedTest(name = "{0}일 때 생성할 수 없다.")
    @NullAndEmptySource
    void 예약자_명은_한_글자_이상이여야_한다(String name) {
        // when & then
        assertThatThrownBy(() -> Organizer.create(name, "010-1234-5678", "1234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 명은 비어 있을 수 없습니다.");
    }

    @ParameterizedTest(name = "{0}일 때 생성할 수 없다.")
    @NullAndEmptySource
    @ValueSource(strings = {"01012345678", "01-1234-5678"})
    void 전화번호는_하이픈이_포함된_핸드폰_양식이어야_한다(String phoneNumber) {
        // when & then
        assertThatThrownBy(() -> Organizer.create("예약자", phoneNumber, "1234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("전화번호 양식은 하이픈을 포함해 핸드폰 양식으로 작성해야 합니다. (010-0000-0000)");
    }

    @ParameterizedTest(name = "{0}일 때 생성할 수 없다.")
    @NullAndEmptySource
    void 비밀번호는_한_글자_이상이여야_한다(String password) {
        // when & then
        assertThatThrownBy(() -> Organizer.create("예약자", "010-1234-5678", password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 비어 있을 수 없습니다.");
    }

    @Test
    void 비밀번호가_일치하는지_확인한다() {
        // given
        Organizer organizer = Organizer.create("예약자", "010-1234-5678", "1234");

        // when
        boolean actual = organizer.matchPassword("1234");

        // then
        assertThat(actual).isTrue();
    }
}
