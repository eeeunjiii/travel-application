package travel.travelapplication.plan.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.plan.domain.Plan;
import travel.travelapplication.plan.application.PlanService;
import travel.travelapplication.user.application.UserService;
import travel.travelapplication.user.domain.SavedPlan;
import travel.travelapplication.user.domain.User;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/plans")
@Slf4j
public class PlanController {
    private final PlanService planService;
    private final UserService userService;

    @GetMapping("/community")
    public String allPlans(Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2User) throws IllegalAccessException {
        User user = userService.findUserByEmail(oAuth2User);

        List<Plan> plans = planService.findAll();
        List<SavedPlan> savedPlans=user.getSavedPlans();

        model.addAttribute("plans", plans);
        model.addAttribute("savedPlans", savedPlans);

        return "test/communityList";
    }

    @GetMapping("/community/{planId}")
    public String plan(@PathVariable("planId") ObjectId planId, Model model) {
        Plan plan=planService.findById(planId);
        model.addAttribute("plan", plan);
        return "test/plan";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> findPlan(@RequestParam(name = "id") ObjectId id){
        Plan plan = planService.findById(id);
        return ResponseEntity.ok(plan);
    }

    @DeleteMapping("/{id}")
    public void deletePlan(@RequestParam(name = "id") ObjectId id){
    }

    @PostMapping
    public ResponseEntity<SavedPlan> savePlan(@RequestParam(name = "id") ObjectId id){
        SavedPlan savedPlan = new SavedPlan(planService.findById(id));
        return ResponseEntity.ok(savedPlan);
    }
  
    @GetMapping("/search") // 특정 장소가 포함된 지도 조회 (키워드가 없으면 모두 출력)
    public String planView(Model model,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           @RequestParam(value = "target", required = false) String target) { // target: map, place 구분 (select)

        if(keyword!=null) {
            List<Plan> findPlans = planService.searchByPlace(keyword);
            model.addAttribute("findPlans", findPlans);
        } else {
            List<Plan> plans=planService.findAll();
            model.addAttribute("plans", plans);
        }
        return null;
    }
}
