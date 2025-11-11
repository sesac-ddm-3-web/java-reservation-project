package spring_junyeong.__meetingRoom.domain.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 1. DTO에 붙일 어노테이션 (@ValidTimeRange)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeRangeValidator.class) // 검증 로직을 담당할 클래스 지정
public @interface ValidTimeRange {
    String message() default "시작 시간은 종료 시간보다 늦을 수 없습니다."; // 기본 오류 메시지
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

