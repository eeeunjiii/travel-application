package travel.travelapplication.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import travel.travelapplication.constant.Status;
import travel.travelapplication.place.application.PlaceService;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.plan.application.PlanService;
import travel.travelapplication.user.domain.User;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import travel.travelapplication.plan.domain.Plan;
import travel.travelapplication.user.domain.UserPlan;
import travel.travelapplication.plan.repository.PlanRepository;
import travel.travelapplication.user.repository.UserPlanRepository;

import java.util.*;

import static travel.travelapplication.dto.userplan.UserPlanDto.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserPlanService {

    private final UserService userService;
    private final UserPlanRepository userPlanRepository;
    private final PlanRepository planRepository;
    private final PlanService planService;
  
    public UserPlan createNewUserPlan(User user, UserPlanInfoDto userPlanInfoDto) {
        UserPlan userPlan = userPlanInfoDto.toEntity();

        UserPlan savedUserPlan = userPlanRepository.insert(userPlan);

        List<UserPlan> userPlans = user.getUserPlans();
        userPlans.add(savedUserPlan);

        user.update(user);
        userService.save(user);
      
        if(isPublic(userPlan.getStatus())) {
            shareUserPlan(savedUserPlan, user);
        }
      
        return userPlan;
    }

    public List<UserPlan> findAllUserPlan(User user) {
        return user.getUserPlans();
    }

    public UserPlan findUserPlanById(ObjectId userPlanId) throws IllegalAccessException {
        UserPlan userPlan = userPlanRepository.findById(userPlanId)
                .orElse(null);

        if (userPlan != null) {
            System.out.println("user plan found");
            return userPlan;
        } else {
            throw new IllegalAccessException("존재하지 않는 여행 일정입니다.");
        }
    }
  
    public void shareUserPlan(UserPlan userPlan, User user) { // 공개 처리 -> 커뮤니티 공유
        Plan existingPlan=planRepository.findByUserPlan(userPlan);

        if(existingPlan!=null) {
            planService.updatePlanFromUserPlanInfo(existingPlan, userPlan);
        } else {
            Plan plan=new Plan(userPlan.getName(), userPlan,  user.getEmail(), new LinkedList<>());
            planRepository.insert(plan);
        }
    }

    private boolean isPublic(Status status) {
        return status.equals(Status.PUBLIC);
    }

    public void updateUserPlanPlaces(UserPlan userPlan, List<Place> places) {
        UserPlan updatedUserPlan = UserPlan.builder()
                .name(userPlan.getName())
                .startDate(userPlan.getStartDate())
                .endDate(userPlan.getEndDate())
                .budget(userPlan.getBudget())
                .city(userPlan.getCity())
                .district(userPlan.getDistrict())
                .status(userPlan.getStatus())
                .places(places)
                .routes(userPlan.getRoutes())
                .build();

        userPlan.update(updatedUserPlan);
        save(userPlan);
    }

    public void save(UserPlan userPlan) {
        userPlanRepository.save(userPlan);
    }

    public void updateUserPlanInfo(User user, UserPlan userPlan, UpdateUserPlanInfoDto userPlanInfoDto) {
        UserPlan updatedUserPlan=updateNameAndStatus(userPlan, userPlanInfoDto);

        user.update(user);
        userService.save(user);

        if(isPublic(userPlan.getStatus())) {
            shareUserPlan(updatedUserPlan, user); // 만약 공개로 그대로 두고, 이름을 게속 바꾸면 이름만 다른 동일한 일정이 계속 저장되는 문제 발생
        } else {
            removePlanByUserPlan(updatedUserPlan);
        }
    }

    private UserPlan updateNameAndStatus(UserPlan userPlan, UpdateUserPlanInfoDto userPlanInfoDto) {
        UserPlan updatedUserPlan=UserPlan.builder()
                .name(userPlanInfoDto.getName())
                .startDate(userPlan.getStartDate())
                .endDate(userPlan.getEndDate())
                .budget(userPlan.getBudget())
                .city(userPlan.getCity())
                .district(userPlan.getDistrict())
                .status(userPlanInfoDto.getStatus())
                .places(userPlan.getPlaces())
                .routes(userPlan.getRoutes())
                .build();

        userPlan.update(updatedUserPlan);
        save(userPlan);
        return userPlan;
    }

    private void removePlanByUserPlan(UserPlan userPlan) {
        Plan planToRemove=planRepository.findByUserPlan(userPlan);
        if(planToRemove!=null) {
            planRepository.delete(planToRemove);
        }
    }
}
