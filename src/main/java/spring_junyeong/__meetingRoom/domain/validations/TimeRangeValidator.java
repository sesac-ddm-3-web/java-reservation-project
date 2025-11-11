package spring_junyeong.__meetingRoom.domain.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import spring_junyeong.__meetingRoom.domain.dto.ReservationDto;

// 2. 검증 로직 클래스 (TimeRangeValidator)
public class TimeRangeValidator implements ConstraintValidator<ValidTimeRange, ReservationDto> {
    @Override
    public boolean isValid(ReservationDto dto, ConstraintValidatorContext context) {
        // null 체크: @NotNull이 이미 처리했겠지만 안전하게 다시 체크
        if (dto.getStartTime() == null || dto.getEndTime() == null) {
            return true;
        }

        // 핵심 로직: 시작 시간이 종료 시간보다 이전이거나 같아야 유효함
        return dto.getStartTime().isBefore(dto.getEndTime());
    }
}
