package travel.travelapplication.place.presentation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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


    @PostMapping("/add-like")
    public ResponseEntity<String> addLike(@RequestBody String placeId,
                                          @AuthenticationPrincipal CustomOAuth2User oAuth2User)
            throws IllegalAccessException {
        User user = userService.findUserByEmail(oAuth2User);
        Place place = placeService.findByPlaceId(placeId);
        if (place != null && user != null) {
            user.addLikedPlace(place);
            userService.save(user);
            System.out.println("Add like: " + place.toString());
            return ResponseEntity.ok("Like added successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Place or User not found");
    }

    @DeleteMapping("/del-like")
    public ResponseEntity<String> delLike(@RequestBody String placeId,
                                          @AuthenticationPrincipal CustomOAuth2User oAuth2User)
            throws IllegalAccessException {
        Place place = placeService.findByPlaceId(placeId);
        User user = userService.findUserByEmail(oAuth2User);

        if (place != null && user != null) {
            user.delLikedPlace(place);
            userService.save(user); // Save user with updated liked places
            return ResponseEntity.ok("Like deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Place or User not found");
        }
    }
}
