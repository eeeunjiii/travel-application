package travel.travelapplication.user.application;

import lombok.RequiredArgsConstructor;
import travel.travelapplication.place.application.PlaceService;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.user.domain.User;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import travel.travelapplication.dto.userplan.LikedPlaceList;
import travel.travelapplication.plan.domain.Plan;
import travel.travelapplication.user.domain.UserPlan;
import travel.travelapplication.plan.repository.PlanRepository;
import travel.travelapplication.user.repository.UserPlanRepository;

import java.util.List;

import static travel.travelapplication.dto.userplan.UserPlanDto.*;

@Service
@RequiredArgsConstructor
public class UserPlanService {
  
    private final UserService userService;
    private final PlaceService placeService;
    private final UserPlanRepository userPlanRepository;
    private final PlanRepository planRepository;

    // 1. 여행 사전정보(초기 일정 생성) 2. 추천 여행 장소 중 선택하여 일정 생성(기존 일정 업데이트)
    public void createNewUserPlan(User user, UserPlanInfoDto userPlanInfoDto) {
        UserPlan userPlan = userPlanInfoDto.toEntity();

        UserPlan savedUserPlan = userPlanRepository.insert(userPlan);

        List<UserPlan> userPlans = user.getUserPlans();
        userPlans.add(savedUserPlan);

        user.update(user);
        userService.save(user);
    }

    public List<UserPlan> findAllUserPlan(User user) {
        return user.getUserPlans();
    }

    public void updateUserPlan(ObjectId userPlanId, UpdateUserPlanInfoDto userPlanInfoDto) {
        userPlanRepository.findById(userPlanId)
                .ifPresent(userPlan ->
                        userPlan.updateUserPlan(userPlanInfoDto.getName(), userPlanInfoDto.getStatus()));
    }

    public UserPlan findUserPlanById(ObjectId userPlanId) throws IllegalAccessException {
        UserPlan userPlan = userPlanRepository.findById(userPlanId)
                .orElse(null);

        if(userPlan!=null) {
            return userPlan;
        } else {
            throw new IllegalAccessException("존재하지 않는 여행 일정입니다.");
        }
    }

    public void shareUserPlan(UserPlan userPlan) { // 공개 처리 -> 커뮤니티 공유
        Plan plan=new Plan(userPlan.getName(), userPlan);
        planRepository.insert(plan);
    }

    public void savePlaceToUserPlan(User user, UserPlan userPlan,
                                    LikedPlaceList likedPlaceList) throws IllegalAccessException {
        List<Place> likedPlaces = user.getLikedPlaces();

        for(Long likedPlaceId : likedPlaceList.getLikedPlaces()) {
            Place place = placeService.findById(likedPlaceId);
            userPlan.addPlaces(place);
            likedPlaces.add(place);
        }

        UserPlan savedUserPlan = userPlanRepository.save(userPlan);
        userService.updateUserPlan(user, savedUserPlan, likedPlaces);
    }
}
