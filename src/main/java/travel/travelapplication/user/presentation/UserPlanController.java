package travel.travelapplication.user.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.auth.dto.SessionUser;
import travel.travelapplication.constant.Status;
import travel.travelapplication.dto.user.UserDto;
import travel.travelapplication.dto.userplan.LikedPlaceList;
import travel.travelapplication.dto.userplan.SelectedPlaceDto;
import travel.travelapplication.dto.userplan.UserPlanDto;
import travel.travelapplication.place.application.PlaceService;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.user.application.UserService;
import travel.travelapplication.user.domain.User;
import travel.travelapplication.user.domain.UserPlan;
import travel.travelapplication.user.application.UserPlanService;

import java.util.*;

import static travel.travelapplication.dto.userplan.UserPlanDto.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user-plan")
public class UserPlanController {

    private final UserPlanService userPlanService;
    private final UserService userService;
    private final PlaceService placeService;

    @GetMapping("/single")
    public String userPlan() {
        return "html/user-plan";
    }

    @GetMapping("/list")
    public String userPlanList() {
        return "html/user-plan-list";
    }

    @GetMapping("/new")
    public String createUserPlan(Model model) {
        model.addAttribute("userPlan", new UserPlanInfoDto());
        model.addAttribute("infoSubmitted", false);

        return "html/new-user-plan";
    }

    @ModelAttribute("statuses")
    public Status[] statuses() {
        return Status.values();
    }

    @ModelAttribute("cities")
    public Map<String, String> cities() {
        Map<String, String> cities = new LinkedHashMap<>();
        cities.put("SEOUL", "서울");
        cities.put("GYEONGGI", "경기");
        cities.put("INCHEON", "인천");
        cities.put("GANGWON", "강원");
        cities.put("DAEJEON", "대전");
        cities.put("SEJONG", "세종");
        cities.put("CHUNGNAM", "충남");
        cities.put("CHUNGBUK", "충북");
        cities.put("BUSAN", "부산");
        cities.put("ULSAN", "울산");
        cities.put("GYEONGNAM", "경남");
        cities.put("GYEONGBUK", "경북");
        cities.put("DAEGU", "대구");
        cities.put("GWANGJU", "광주");
        cities.put("JEONNAM", "전남");
        cities.put("JEONBUK", "전북");
        cities.put("JEJU", "제주");
        return cities;
    }

    @GetMapping("/districts")
    @ResponseBody
    public List<String> getDistricts(@RequestParam("city") String city) {
        List<String> districts = new ArrayList<>();

        if ("SEOUL".equals(city)) {
            districts.add("강남구");
            districts.add("강동구");
            districts.add("강북구");
            districts.add("강서구");
            districts.add("관악구");
        } else if ("GYEONGGI".equals(city)) {
            districts.add("광명시");
            districts.add("광주시");
            districts.add("과천시");
            districts.add("구리시");
            districts.add("군포시");
        } else if ("INCHEON".equals(city)) {
            districts.add("연수구");
            districts.add("중구");
            districts.add("부평구");
            districts.add("남동구");
            districts.add("서구");
        }
        return districts;
    }

//    @GetMapping("/plan-info")
//    public String userPlanInfoForm(Model model) {
//        model.addAttribute("userPlan", new UserPlanInfoDto());
//        return "test/getPlanInfoForm";
//    }

    @PostMapping("/plan-info")
    public String saveUserPlan(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
                               @ModelAttribute("userPlan") UserPlanInfoDto userPlan,
                               @ModelAttribute("user") UserDto user, Model model,
                               HttpServletRequest request)
            throws IllegalAccessException {
        User userInfo = userService.findUserByEmail(oAuth2User);

        log.info("user.name: {}", userInfo.getName());
        log.info("user.email: {}", userInfo.getEmail());

        log.info("userPlan.name: {}", userPlan.getName());
        log.info("userPlan.startDate: {}", userPlan.getStartDate());
        log.info("userPlan.endDate: {}", userPlan.getEndDate());
        log.info("userPlan.budget: {}", userPlan.getBudget());
        log.info("userPlan.city: {}", userPlan.getCity());
        log.info("userPlan.district: {}", userPlan.getDistrict());
        log.info("userPlan.status: {}", userPlan.getStatus());

        userPlanService.createNewUserPlan(userInfo, userPlan);
        model.addAttribute("infoSubmitted", true);

        model.addAttribute("userPlan", userPlan);
        model.addAttribute("user", userInfo);
        return "html/new-user-plan";
    }

    @GetMapping("/{userPlanId}")
    public String userPlan(@PathVariable("userPlanId") ObjectId userPlanId, Model model) throws IllegalAccessException {
        UserPlan userPlan = userPlanService.findUserPlanById(userPlanId);
        model.addAttribute("userPlan", userPlan);

        return "user-plan";
    }

    @GetMapping("/{userPlanId}/places")
    public String selectLikedPlaces(HttpServletRequest request, Model model,
                                    @PathVariable("userPlanId") ObjectId userPlanId) throws IllegalAccessException {
        UserPlan userPlan = userPlanService.findUserPlanById(userPlanId);

        HttpSession session = request.getSession();
        List<SessionUser> places = (List<SessionUser>) session.getAttribute("recommendation-result");

        model.addAttribute("userPlan", userPlan);
        model.addAttribute("likedPlaceList", new LikedPlaceList());
        model.addAttribute("places", places);

        return "test/selectLikedPlacesForm";
    }

    @PostMapping("/save-places")
    public ResponseEntity<Map<String, Object>> savePlacesToUserPlan(@RequestBody List<String> selectedPlaceId,
                                                                    Model model,
                                                                    @ModelAttribute("userPlan") UserPlanInfoDto userPlan,
                                                                    @AuthenticationPrincipal CustomOAuth2User oAuth2User)
            throws IllegalAccessException {
        User user = userService.findUserByEmail(oAuth2User);
        List<Place> selectedPlaces = new ArrayList<>();

        for (String placeId : selectedPlaceId) {
            Place place = placeService.findByPlaceId(placeId);
            selectedPlaces.add(place);
        }
        model.addAttribute("selectedPlaces", selectedPlaces);
        userPlanService.savePlaceToNewUserPlan(user, selectedPlaceId);

        // JSON으로 변환
        Map<String, Object> response = new HashMap<>();
        response.put("selectedPlaces", selectedPlaces);
        response.put("userPlan", userPlan);

        log.info(selectedPlaces.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
