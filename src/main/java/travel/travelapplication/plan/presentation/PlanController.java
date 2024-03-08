package travel.travelapplication.plan.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travel.travelapplication.plan.domain.Plan;
import travel.travelapplication.plan.application.PlanService;
import travel.travelapplication.plan.dto.PlanResponse;

@Transactional
@RequiredArgsConstructor
@RestController
@RequestMapping("/plans")
public class PlanController {
    private final PlanService planService;
    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> findPlan(@RequestParam(name = "id") Long id){
        Plan plan = planService.findById(id);
        return ResponseEntity.ok(new PlanResponse(plan.getUserPlan()));
    }

    @DeleteMapping("/{id}")
    public void deletePlan(@RequestParam(name = "id") Long id){

    }


}
