package SpringMiniProject.SeSAC.MeetingRoom.Management;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MeetingRoomManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingRoomManagementApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setFieldMatchingEnabled(true)
				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
//				.setMatchingStrategy(MatchingStrategies.STRICT);//ReservationDto에서 Reservation으로 넘어갈 때 assignedMonth만 매핑이 되지 않아 설정 추가
		return modelMapper;
		//return new modelMapper 이라고 작성해서 위의 설정들이 아무것도 적용되지 않은 modelMapper가 반환되었다
		//그래서 자꾸 object value <-> entity 변환이 이상했음 null 값으로 반환되고 저장 안되고 그랬음
	}
}