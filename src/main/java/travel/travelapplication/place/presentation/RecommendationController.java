package travel.travelapplication.place.presentation;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import travel.travelapplication.place.application.RecommendationService;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.place.domain.Recommendation;

@RestController
public class RecommendationController {
    private final RecommendationService service;

    @GetMapping("/recommendations")
    public CompletableFuture<List<Recommendation>> fetchData() {
        return service.fetchData();
    }

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }
}
