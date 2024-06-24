package travel.travelapplication.plan.presentation;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import travel.travelapplication.plan.domain.Plan;
import travel.travelapplication.plan.application.PlanService;

import java.util.List;
import travel.travelapplication.plan.domain.SavedPlan;

@Transactional
@RequiredArgsConstructor
@Controller
@RequestMapping("/plans")
public class PlanController {
    private PlanService planService;

    @GetMapping
    public String showPlansPage() {
        return "plans";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> findPlan(@RequestParam(name = "id") ObjectId id) {
        Plan plan = planService.findById(id);
        return ResponseEntity.ok(plan);
    }

    @DeleteMapping("/{id}")
    public void deletePlan(@RequestParam(name = "id") ObjectId id) {
    }

    @PostMapping
    public ResponseEntity<SavedPlan> savePlan(@RequestParam(name = "id") ObjectId id) {
        SavedPlan savedPlan = new SavedPlan(planService.findById(id));
        return ResponseEntity.ok(savedPlan);
    }

    @GetMapping("/search")
    public String planView(Model model, @RequestParam(value = "keyword", required = false) String keyword) {

        if (keyword != null) {
            List<Plan> findPlans = planService.searchByPlace(keyword);
            model.addAttribute("findPlans", findPlans);
        } else {
            List<Plan> plans = planService.findAll();
            model.addAttribute("plans", plans);
        }
        return null;
    }
}
