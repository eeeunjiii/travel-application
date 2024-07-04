package travel.travelapplication.place.application;

import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.place.domain.Recommendation;

@Service
@Getter
public class RecommendationService {
    private static final String url = "http://127.0.0.1:5000/data"; //추후 사용자 id 매핑 필요

    private final RestTemplate restTemplate;

    @Autowired
    public RecommendationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Recommendation> fetchData() {
        ResponseEntity<List<Recommendation>> response = restTemplate.exchange(
                "http://127.0.0.1:5000/data",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Recommendation>>() {
                }
        );
        return response.getBody();
    }

}
