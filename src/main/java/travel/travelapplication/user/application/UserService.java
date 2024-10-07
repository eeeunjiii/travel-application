package travel.travelapplication.user.application;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.place.domain.Tag;
import travel.travelapplication.place.repository.PlaceRepository;
import travel.travelapplication.place.repository.TagRepository;
import travel.travelapplication.plan.domain.Plan;
import travel.travelapplication.user.domain.User;
import travel.travelapplication.user.domain.UserPlan;
import travel.travelapplication.user.repository.UserPlanRepository;
import travel.travelapplication.user.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final UserPlanRepository userPlanRepository;
    private final PlaceRepository placeRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public void updateUserName(String email, String username) throws IllegalAccessException {
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user != null) {
            User updatedUser = User.builder()
                    .name(username)
                    .email(user.getEmail())
                    .accessToken(user.getAccessToken())
                    .userPlans(user.getUserPlans())
                    .savedPlans(user.getSavedPlans())
                    .tags(user.getTags())
                    .likedPlaces(user.getLikedPlaces())
                    .role(user.getRole())
                    .build();

            user.update(updatedUser);
            save(user);
        } else {
            throw new IllegalAccessException("존재하지 않는 사용자입니다.");
        }
    }

    public void addTag(User user, List<String> tagIds) throws IllegalAccessException {
        List<ObjectId> objectIdList = tagIds.stream()
                .map(ObjectId::new)
                .collect(Collectors.toList());
        List<Tag> tags = tagRepository.findAllById(objectIdList);

        if (user != null) {
            User updatedUser = User.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .accessToken(user.getAccessToken())
                    .userPlans(user.getUserPlans())
                    .savedPlans(user.getSavedPlans())
                    .tags(tags)
                    .likedPlaces(user.getLikedPlaces())
                    .role(user.getRole())
                    .build();

            user.update(updatedUser);
            save(user);
        } else {
            throw new IllegalAccessException("존재하지 않는 사용자입니다.");
        }
    }

    public void updateUserPlan(User user, UserPlan userPlan,
                               List<Place> userPlanPlaces) throws IllegalAccessException {
        if (userPlan != null) {
            UserPlan updatedUserPlan = UserPlan.builder()
                    .name(userPlan.getName())
                    .startDate(userPlan.getStartDate())
                    .endDate(userPlan.getEndDate())
                    .budget(userPlan.getBudget())
                    .city(userPlan.getCity())
                    .district(userPlan.getDistrict())
                    .status(userPlan.getStatus())
                    .places(userPlanPlaces)
                    .routes(userPlan.getRoutes())
                    .build();

            userPlan.update(updatedUserPlan);
            userPlanRepository.save(userPlan);
        }

        if (user != null) {
            User updatedUser = User.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .accessToken(user.getAccessToken())
                    .userPlans(user.getUserPlans())
                    .savedPlans(user.getSavedPlans())
                    .tags(user.getTags())
                    .likedPlaces(user.getLikedPlaces())
                    .role(user.getRole())
                    .build();

            user.update(updatedUser);
            save(user);
        } else {
            throw new IllegalAccessException("존재하지 않는 사용자입니다.");
        }
    }

    public void updateUserSavedPlans(User user, List<Plan> savedPlans) {
        User updatedUser = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .userPlans(user.getUserPlans())
                .tags(user.getTags())
                .likedPlaces(user.getLikedPlaces())
                .savedPlans(savedPlans)
                .role(user.getRole())
                .accessToken(user.getAccessToken())
                .build();

        user.update(updatedUser);
        save(user);
    }

    public List<Tag> findAllTag(User user) throws IllegalAccessException {
        return user.getTags();
    }

    public User findUserByEmail(@AuthenticationPrincipal CustomOAuth2User oAuth2User) throws IllegalAccessException {
        if (oAuth2User == null) {
            throw new IllegalAccessException("인증되지 않은 사용자입니다. 로그인 필요");
        }

        String provider = oAuth2User.getRegistrationId();
        String email;

        if (provider.equals("google")) {
            email = oAuth2User.getEmail();
        } else {
            Map<Object, String> response = (Map<Object, String>) oAuth2User.getAttribute("response");
            email = response.get("email");
        }

        User user = userRepository.findByEmail(email)
                .orElse(null);
        if (user != null) {
            return user;
        } else {
            throw new IllegalAccessException("존재하지 않는 사용자입니다.");
        }
    }

    public void addLike(ObjectId userId, Place place) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Place> likedPlaces = user.getLikedPlaces();
        if (!likedPlaces.contains(place)) {
            user.addLikedPlace(place);
        }
        userRepository.save(user);
    }

    public void delLike(ObjectId userId, Place place) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.delLikedPlace(place);
        userRepository.save(user);
    }
}
