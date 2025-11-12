package SpringMiniProject.SeSAC.MeetingRoom.Management.Application;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service //bean 등록 안 해주니까 인텔리제이가 알려줌
@Validated
public class ValidationService {
    public <T> void checkValid(@Valid T vaidationTarget) {
        //do nothing 그냥 실습
    }
}
