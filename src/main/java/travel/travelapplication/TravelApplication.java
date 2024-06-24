package travel.travelapplication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.lang.*;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableMongoRepositories
public class TravelApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TravelApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:5000/data";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String jsonData = response.getBody();
        System.out.println(jsonData); // 인코딩 확인용

        // JSON 데이터를 파싱하여 출력
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonData);

        System.out.println(jsonNode.toPrettyString());
    }

}
