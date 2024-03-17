package travel.travelapplication.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.travelapplication.plan.repository.SavedPlanRepository;

@Service
@RequiredArgsConstructor
public class UserPlanService {
    private final SavedPlanRepository savedPlanRepository;

}
