package travel.travelapplication.place.presentation;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import travel.travelapplication.place.application.RecommendationService;
import travel.travelapplication.place.domain.Recommendation;
import travel.travelapplication.user.application.UserPlanService;
import travel.travelapplication.user.domain.User;
import travel.travelapplication.user.domain.UserPlan;

@RestController
public class RecommendationController {
    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

//    @PostMapping("/recommend-places")  // 프론트에서 호출할 엔드포인트
//    public Mono<ResponseEntity<List<Recommendation>>> getRecommendations(
//            @RequestBody UserPlan userPlan, @RequestBody User user) {
//
//        return Mono.fromCallable(() -> service.sendUserPlanInfo(userPlan, user))
//                .map(recommendations -> ResponseEntity.ok(recommendations))
//                .onErrorReturn(ResponseEntity.status(500).body(List.of())); // 에러 발생 시 빈 리스트 반환
//    }

    @GetMapping("/recommendations")
    public CompletableFuture<List<Recommendation>> fetchData(@RequestParam String endPoint) {
        return service.fetchData(endPoint);
    }
}