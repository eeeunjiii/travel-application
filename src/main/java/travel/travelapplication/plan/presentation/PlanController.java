package travel.travelapplication.plan.presentation;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
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
import travel.travelapplication.user.domain.SavedPlan;
      
import java.util.List;      

@Transactional
@RequiredArgsConstructor
@RestController
@RequestMapping("/plans")
public class PlanController {
    private PlanService planService;
  
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
