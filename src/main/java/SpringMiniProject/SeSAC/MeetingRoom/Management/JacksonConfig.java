package SpringMiniProject.SeSAC.MeetingRoom.Management;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



//Mapper를 설정한 Config 파일의 디렉토리 위치를 Management 패키지 아래 안 두고
//그냥 main/java 밑에 둬서 @ComponentScan이 되지 않고 있었음..
@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.registerModule(new JavaTimeModule()); //Reservation의 LocalDateTime 필드를 GetResponseReservationDto의 LocalDateTime 필드로 변환할 때 Mapper의 Json 변환 형식 지정
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}