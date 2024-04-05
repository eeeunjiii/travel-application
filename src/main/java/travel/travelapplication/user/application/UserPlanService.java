package travel.travelapplication.user.application;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.travelapplication.user.domain.UserPlan;
import travel.travelapplication.user.repository.SavedPlanRepository;
import travel.travelapplication.user.repository.UserPlanRepository;

@Service
@AllArgsConstructor
public class UserPlanService {
    private UserPlanRepository repository;

}
