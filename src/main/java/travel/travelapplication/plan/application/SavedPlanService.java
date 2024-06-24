package travel.travelapplication.plan.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.travelapplication.plan.repository.SavedPlanRepository;

@RequiredArgsConstructor
@Service
public class SavedPlanService {
    private final SavedPlanRepository savedPlanRepository;

}
