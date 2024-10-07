package travel.travelapplication.place.presentation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.place.application.PlaceService;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.user.application.UserService;
import travel.travelapplication.user.domain.User;

@Transactional
@Controller
@RequiredArgsConstructor
@RequestMapping("/places")
@Slf4j
public class PlaceController {
    private final PlaceService placeService;
    private final UserService userService;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Place> findPlaceById(@RequestParam(name = "id") ObjectId id) {
        Place place = placeService.findById(id);
        return ResponseEntity.ok(place);
    }

    @GetMapping("/{name}")
    @ResponseBody
    public ResponseEntity<Place> findPlaceByName(@RequestParam(name = "name") String name) {
        Place place = placeService.findByName(name);
        return ResponseEntity.ok(place);
    }

    @ResponseBody
    @GetMapping("/user-id-data")
    public Long sendUserId() {
        return 112L; // 로그인한 사용자의 user_id 반환하기
    }

    @PostMapping("/like/{placeId}")
    public ResponseEntity<Boolean> addLikePlace(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
                                                @PathVariable("placeId") Long placeId) throws IllegalAccessException {
        User user=userService.findUserByEmail(oAuth2User);

        boolean isNowLiked=placeService.toggleLikePlace(user, placeId);

        return ResponseEntity.ok(isNowLiked);
    }
}
