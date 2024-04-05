package travel.travelapplication.user.application;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import travel.travelapplication.user.repository.SavedPlanRepository;
import travel.travelapplication.user.domain.SavedPlan;

@AllArgsConstructor
@Service
public class SavedPlanService {
    private SavedPlanRepository repository;

    public List<SavedPlan> findAllSavedPlan() {
        return repository.findAll();
    }


    public SavedPlan findById(ObjectId id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + "not found"));
    }
}
