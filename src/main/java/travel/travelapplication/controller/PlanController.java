package travel.travelapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import travel.travelapplication.entity.Plan;
import travel.travelapplication.service.PlanService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @GetMapping("/search")
    public String planView(Model model, @RequestParam(value = "keyword", required = false) String keyword) {

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
