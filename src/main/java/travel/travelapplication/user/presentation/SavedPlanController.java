package travel.travelapplication.user.presentation;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travel.travelapplication.user.application.SavedPlanService;
import travel.travelapplication.user.domain.SavedPlan;

@Transactional
@RestController
@RequestMapping("/saved-plan")
public class SavedPlanController {
    private SavedPlanService service;
    @GetMapping("/{id}")
    public List<SavedPlan> getSavedPlan(){
        return service.findAllSavedPlan();
    }
}
