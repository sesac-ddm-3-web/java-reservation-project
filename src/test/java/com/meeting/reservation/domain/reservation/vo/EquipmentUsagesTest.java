package com.meeting.reservation.domain.reservation.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.meeting.reservation.domain.equipment.vo.EquipmentId;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EquipmentUsagesTest {

    @Test
    void 비품_사용_정보_목록을_생성한다() {
        // given
        EquipmentUsage usage = EquipmentUsage.create(EquipmentId.create(1L), "프로젝터", 5);
        Map<EquipmentId, EquipmentUsage> usageMap = new HashMap<>();
        usageMap.put(EquipmentId.create(1L), usage);

        // when
        EquipmentUsages actual = EquipmentUsages.create(usageMap);

        // then
        assertThat(actual).isInstanceOf(EquipmentUsages.class);
    }

    @Test
    void 비품_ID로_수량을_찾는다() {
        // given
        EquipmentUsage usage = EquipmentUsage.create(EquipmentId.create(1L), "프로젝터", 5);
        Map<EquipmentId, EquipmentUsage> usageMap = new HashMap<>();
        usageMap.put(EquipmentId.create(1L), usage);
        EquipmentUsages usages = EquipmentUsages.create(usageMap);

        // when
        int actual = usages.getQuantity(EquipmentId.create(1L));

        // then
        assertThat(actual).isEqualTo(5);
    }

    @Test
    void 존재하지_않는_비품_ID로_사용_수량으로_0을_조회한다() {
        // given
        EquipmentUsage usage = EquipmentUsage.create(EquipmentId.create(1L), "프로젝터", 5);
        Map<EquipmentId, EquipmentUsage> usageMap = new HashMap<>();
        usageMap.put(EquipmentId.create(1L), usage);
        EquipmentUsages usages = EquipmentUsages.create(usageMap);

        // when
        int actual = usages.getQuantity(EquipmentId.create(999L));

        // then
        assertThat(actual).isZero();
    }

    @Test
    void 여러_비품_사용_정보를_관리한다() {
        // given
        EquipmentUsage usage1 = EquipmentUsage.create(EquipmentId.create(1L), "프로젝터", 5);
        EquipmentUsage usage2 = EquipmentUsage.create(EquipmentId.create(2L), "프로젝터", 10);
        EquipmentUsage usage3 = EquipmentUsage.create(EquipmentId.create(3L), "프로젝터", 3);
        Map<EquipmentId, EquipmentUsage> usageMap = new HashMap<>();
        usageMap.put(EquipmentId.create(1L), usage1);
        usageMap.put(EquipmentId.create(2L), usage2);
        usageMap.put(EquipmentId.create(3L), usage3);
        EquipmentUsages usages = EquipmentUsages.create(usageMap);

        // when & then
        assertAll(
                () -> assertThat(usages.getQuantity(EquipmentId.create(1L))).isEqualTo(5),
                () -> assertThat(usages.getQuantity(EquipmentId.create(2L))).isEqualTo(10),
                () -> assertThat(usages.getQuantity(EquipmentId.create(3L))).isEqualTo(3)
        );
    }

    @Test
    void 비품_사용_정보_목록은_불변_Map으로_반환된다() {
        // given
        EquipmentUsage usage = EquipmentUsage.create(EquipmentId.create(1L), "프로젝터", 5);
        Map<EquipmentId, EquipmentUsage> usageMap = new HashMap<>();
        usageMap.put(EquipmentId.create(1L), usage);
        EquipmentUsages usages = EquipmentUsages.create(usageMap);

        // when
        Map<EquipmentId, EquipmentUsage> actual = usages.getEquipmentUsages();

        // then
        assertThatThrownBy(() -> actual.put(EquipmentId.create(2L), EquipmentUsage.create(EquipmentId.create(2L), "프로젝터", 10)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void 비품_사용_정보_목록의_모든_항목을_조회한다() {
        // given
        EquipmentUsage usage1 = EquipmentUsage.create(EquipmentId.create(1L), "프로젝터", 5);
        EquipmentUsage usage2 = EquipmentUsage.create(EquipmentId.create(2L), "프로젝터", 10);
        Map<EquipmentId, EquipmentUsage> usageMap = new HashMap<>();
        usageMap.put(EquipmentId.create(1L), usage1);
        usageMap.put(EquipmentId.create(2L), usage2);
        EquipmentUsages usages = EquipmentUsages.create(usageMap);

        // when
        Map<EquipmentId, EquipmentUsage> actual = usages.getEquipmentUsages();

        // then
        assertThat(actual).hasSize(2);
    }
}
