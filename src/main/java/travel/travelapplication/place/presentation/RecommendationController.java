package travel.travelapplication.place.presentation;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import travel.travelapplication.place.application.RecommendationService;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.place.domain.Recommendation;

@RestController
public class RecommendationController {
    private final RecommendationService service;

    @GetMapping("/data")
    public List<Recommendation> fetchData() {
        return (List<Recommendation>) service.fetchData();
    }

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }
}
