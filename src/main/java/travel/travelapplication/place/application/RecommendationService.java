package travel.travelapplication.place.application;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import travel.travelapplication.auth.dto.SessionUser;
import travel.travelapplication.dto.userplan.UserPlanInfoRequest;
import travel.travelapplication.place.domain.Recommendation;
import travel.travelapplication.user.domain.User;
import travel.travelapplication.user.domain.UserPlan;

@Service
@Getter
public class RecommendationService {

//    private final RestTemplate restTemplate;

    private final WebClient webClient;
    private final HttpSession httpSession;

//    @Autowired
//    public RecommendationService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    public RecommendationService(WebClient.Builder builder, HttpSession httpSession) {
        this.webClient = builder
                .baseUrl("http://127.0.0.1:5000")
                .build();
        this.httpSession=httpSession;
    }


//    @Async
//    public CompletableFuture<List<Recommendation>> fetchData() {
//        ResponseEntity<List<Recommendation>> response = restTemplate.exchange(
//                "http://127.0.0.1:5000/recommendations",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Recommendation>>() {
//                }
//        );
//        List<Recommendation> recommendations = response.getBody();
//        return CompletableFuture.completedFuture(recommendations);
//    }


    public List<Recommendation> sendUserPlanInfo(UserPlan userPlan, User user) {

        long period= ChronoUnit.DAYS.between(userPlan.getStartDate(), userPlan.getEndDate());

        UserPlanInfoRequest request=new UserPlanInfoRequest(user.getEmail(), userPlan.getCity(),
                userPlan.getDistrict(), period);

        return webClient.post()
                .uri("/recommendations")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Recommendation>>() {})
                .block();
    }

    public void savePlacesToSession(List<Recommendation> recommendations) {
        List<SessionUser> sessions = (List<SessionUser>) httpSession.getAttribute("recommendation-result");
        if(sessions == null) {
            sessions = new ArrayList<>();
        }

        for(Recommendation recommendation:recommendations) {
            SessionUser sessionUser = recommendation.toSessionUser();
            sessions.add(sessionUser);
        }
        httpSession.setAttribute("recommendation-result", sessions);
    }
}
