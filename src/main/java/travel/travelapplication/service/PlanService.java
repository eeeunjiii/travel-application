package travel.travelapplication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import travel.travelapplication.entity.Place;
import travel.travelapplication.entity.Plan;
import travel.travelapplication.entity.UserPlan;
import travel.travelapplication.repository.PlanRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanService {

    private final PlanRepository planRepository;

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
