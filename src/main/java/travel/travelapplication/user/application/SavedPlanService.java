package travel.travelapplication.user.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.travelapplication.user.repository.SavedPlanRepository;
import travel.travelapplication.user.domain.SavedPlan;

@RequiredArgsConstructor
@Service
public class SavedPlanService {
    private final SavedPlanRepository repository;

    public List<SavedPlan> findAllSavedPlan() {
        return repository.findAll();
    }
}
