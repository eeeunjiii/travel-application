package travel.travelapplication.place.presentation;

import org.springframework.web.bind.annotation.RestController;
import travel.travelapplication.place.application.RecommendationService;
import travel.travelapplication.plan.repository.PlanRepository;
import travel.travelapplication.user.repository.UserPlanRepository;

@RestController
public class FetchController {
    private final RecommendationService service;
    private final UserPlanRepository userPlanRepository;
    private final PlanRepository planRepository;

    public FetchController(RecommendationService service, UserPlanRepository userPlanRepository,
                           PlanRepository planRepository) {
        this.service = service;
        this.userPlanRepository = userPlanRepository;
        this.planRepository = planRepository;
    }

//    @GetMapping("/recommendations")
//    public CompletableFuture<List<Recommendation>> fetchData() {
//        return service.fetchData();
//    }


}