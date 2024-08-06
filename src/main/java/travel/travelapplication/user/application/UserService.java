package travel.travelapplication.user.application;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.place.domain.Tag;
import travel.travelapplication.place.repository.TagRepository;
import travel.travelapplication.user.domain.User;
import travel.travelapplication.user.domain.UserPlan;
import travel.travelapplication.user.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User updateUserName(String email, String username) throws IllegalAccessException {
        User user=userRepository.findByEmail(email)
                .orElse(null);

        if(user!=null) {
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
            return user;
        } else {
            throw new IllegalAccessException("존재하지 않는 사용자입니다.");
        }
    }

    public void addTag(User user, List<String> tagIds) throws IllegalAccessException {
        List<ObjectId> objectIdList = tagIds.stream()
                .map(ObjectId::new)
                .collect(Collectors.toList());
        List<Tag> tags = tagRepository.findAllById(objectIdList);

        if(user!=null) {
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

    public void updateUserPlan(User user, UserPlan userPlan, List<Place> likedPlaces) throws IllegalAccessException {
        List<UserPlan> userPlans = user.getUserPlans();
        userPlans.add(userPlan);

        if(user!=null) {
            User updatedUser = User.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .accessToken(user.getAccessToken())
                    .userPlans(userPlans)
                    .savedPlans(user.getSavedPlans())
                    .tags(user.getTags())
                    .likedPlaces(likedPlaces)
                    .role(user.getRole())
                    .build();

            user.update(updatedUser);
            save(user);
        } else {
            throw new IllegalAccessException("존재하지 않는 사용자입니다.");
        }
    }

    public List<Tag> findAllTag(User user) throws IllegalAccessException {
        return user.getTags();
    }

    public User findUserByEmail(@AuthenticationPrincipal CustomOAuth2User oAuth2User) throws IllegalAccessException {
        if(oAuth2User==null) {
            throw new IllegalAccessException("인증되지 않은 사용자입니다. 로그인 필요");
        }

        String provider=oAuth2User.getRegistrationId();
        String email;

        if(provider.equals("google")) {
            email= oAuth2User.getEmail();
        } else {
            Map<Object, String> response=(Map<Object, String>) oAuth2User.getAttribute("response");
            email=response.get("email");
        }

        User user=userRepository.findByEmail(email)
                .orElse(null);
        if(user!=null) {
            return user;
        } else {
            throw new IllegalAccessException("존재하지 않는 사용자입니다.");
        }
    }
}
