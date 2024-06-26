package travel.travelapplication.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;  
import travel.travelapplication.user.repository.SavedPlanRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import travel.travelapplication.constant.Status;
import travel.travelapplication.dto.userplan.SelectedPlaceDto;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.plan.domain.Plan;
import travel.travelapplication.user.domain.UserPlan;
import travel.travelapplication.plan.repository.PlanRepository;
import travel.travelapplication.user.repository.UserPlanRepository;
import travel.travelapplication.user.repository.UserRepository;

import java.util.List;

import static travel.travelapplication.dto.userplan.UserPlanDto.*;

@Service
@RequiredArgsConstructor
public class UserPlanService {
  
    private final UserRepository userRepository;
    private final UserPlanRepository userPlanRepository;
    private final PlanRepository planRepository;

    public void saveUserPlan(String email, SelectedPlaceDto selectedPlaceDto, UserPlanInfoDto userPlanInfoDto) { // Userplan
        userRepository.findByEmail(email)
                .ifPresent(user ->
                {
                    UserPlan createdUserPlan=createNewUserPlan(userPlanInfoDto);
                    UserPlan userPlan = savePlaceToUserPlan(createdUserPlan, selectedPlaceDto);
                    user.addUserPlan(userPlan);
                    userPlanRepository.save(userPlan);
                });
    }

    public void updateUserPlan(ObjectId userPlanId, UpdateUserPlanInfoDto userPlanInfoDto) {
        userPlanRepository.findById(userPlanId)
                .ifPresent(userPlan ->
                        userPlan.updateUserPlan(userPlanInfoDto.getName(), userPlanInfoDto.getStatus()));
    }

    public void shareUserPlan(UserPlan userPlan) {
        Plan plan=new Plan(userPlan.getName(), userPlan);
        planRepository.save(plan);
    }

    private UserPlan createNewUserPlan(UserPlanInfoDto userPlanInfoDto) {
        return new UserPlan(userPlanInfoDto.getName(), userPlanInfoDto.getStartDate(),
                userPlanInfoDto.getEndDate(), userPlanInfoDto.getBudget(), Status.PRIVATE, null, null);
    }

    private UserPlan savePlaceToUserPlan(UserPlan userPlans, SelectedPlaceDto selectedPlaceDto) {
        List<Place> places=selectedPlaceDto.getSelectedPlaces();
        for(Place place : places) {
            userPlans.addPlaces(place);
        }
        return userPlans;
    }
}
