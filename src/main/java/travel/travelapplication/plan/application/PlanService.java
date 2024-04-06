package travel.travelapplication.plan.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.plan.domain.Plan;
import travel.travelapplication.user.domain.UserPlan;  
import travel.travelapplication.plan.repository.PlanRepository;

@AllArgsConstructor
@Service
@Slf4j
public class PlanService {
    private PlanRepository planRepository;

    public Plan findById(ObjectId id){
        return planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+id));
    }
  
    public List<Plan> searchByPlace(String keyword) {
        List<Plan> plans = planRepository.findAll();
        List<Plan> findPlans=new ArrayList<>();

        for(Plan plan : plans) {
            UserPlan userPlan = plan.getUserPlan();
            if(findPlaces(userPlan, keyword)) {
                findPlans.add(plan);
            }
        }
        return findPlans;
    }

    private boolean findPlaces(UserPlan userPlan, String keyword) {
        List<Place> places = userPlan.getPlaces();

        for(Place place : places) {
            if(place.getName().contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    public List<Plan> findAll() {
        return planRepository.findAll();
    }
}
