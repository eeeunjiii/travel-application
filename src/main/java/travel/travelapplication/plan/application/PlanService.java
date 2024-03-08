package travel.travelapplication.plan.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.travelapplication.plan.domain.Plan;
import travel.travelapplication.plan.repository.PlanRepository;

@RequiredArgsConstructor
@Service
public class PlanService {
    private final PlanRepository planRepository;

    public Plan findById(Long id){
        return planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+id));
    }
}
