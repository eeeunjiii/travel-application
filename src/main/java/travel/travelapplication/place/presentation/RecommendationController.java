package travel.travelapplication.place.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import travel.travelapplication.place.application.RecommendationService;
import travel.travelapplication.place.domain.Recommendation;

@RestController
public class RecommendationController {
    private final RecommendationService service;

    @GetMapping("/data")
    public List<Recommendation> fetchData() { // CompletableFuture<List<Recommendation>>
        CompletableFuture<List<Recommendation>> recommendations = service.fetchData();
        CompletableFuture<List<Recommendation>> results = recommendations.thenApply(recommendation -> {
            List<Recommendation> list = new ArrayList<>(recommendation);
            return list;
        });

        try {
            return results.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }
}
