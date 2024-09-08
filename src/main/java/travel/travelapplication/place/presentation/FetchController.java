package travel.travelapplication.place.presentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import travel.travelapplication.place.application.RecommendationService;
import travel.travelapplication.place.domain.Recommendation;
import travel.travelapplication.plan.domain.Plan;
import travel.travelapplication.plan.repository.PlanRepository;
import travel.travelapplication.user.domain.UserPlan;
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

    @GetMapping("/recommendations")
    public CompletableFuture<List<Recommendation>> fetchData() {
        return service.fetchData();
    }

    @GetMapping("/user-plan/all")
    public List<UserPlan> getAllPlans()
            throws IllegalAccessException {
        return userPlanRepository.findAll();
    }

    @GetMapping("/community/all")
    public List<Map<String, Object>> getPlans() {
        List<Plan> plans = planRepository.findAll();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Plan plan : plans) {
            Map<String, Object> planMap = new HashMap<>();
            planMap.put("id", plan.getId().toString());  // ObjectId를 명시적으로 포함
            planMap.put("name", plan.getName());
            planMap.put("createdAt", plan.getCreatedAt());
            response.add(planMap);
        }
        return response;
    }

}