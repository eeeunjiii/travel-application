package travel.travelapplication.plan.application;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import travel.travelapplication.plan.domain.Plan;
import travel.travelapplication.plan.repository.PlanRepository;

@AllArgsConstructor
@Service
public class PlanService {
    private PlanRepository planRepository;

    public Plan findById(ObjectId id){
        return planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+id));
    }

}
