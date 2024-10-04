package travel.travelapplication.user.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.auth.dto.SessionUser;
import travel.travelapplication.constant.Status;
import travel.travelapplication.dto.userplan.LikedPlaceList;
import travel.travelapplication.place.application.PlaceService;
import travel.travelapplication.place.application.ProvCityService;
import travel.travelapplication.place.application.RecommendationService;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.place.domain.ProvCity;
import travel.travelapplication.place.domain.Recommendation;
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
    private final RecommendationService recommendationService;
    private final ProvCityService provCityService;

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

//        return "html/new-user-plan";
        return "test/getPlanInfoForm";
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
        Optional<ProvCity> provCities=provCityService.getCity(city);

        if(provCities.isPresent()) {
            ProvCity provCity=provCities.get();
            districts=provCity.getDistricts();
        }

        return districts;
    }

    @PostMapping("/plan-info")
    public String saveUserPlan(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
                               @ModelAttribute("userPlan") UserPlanInfoDto userPlanInfoDto,
                               Model model)
            throws IllegalAccessException {
        User userInfo = userService.findUserByEmail(oAuth2User);

        log.info("user.name: {}", userInfo.getName());
        log.info("user.email: {}", userInfo.getEmail());

        log.info("userPlan.name: {}", userPlanInfoDto.getName());
        log.info("userPlan.startDate: {}", userPlanInfoDto.getStartDate());
        log.info("userPlan.endDate: {}", userPlanInfoDto.getEndDate());
        log.info("userPlan.budget: {}", userPlanInfoDto.getBudget());
        log.info("userPlan.city: {}", userPlanInfoDto.getCity());
        log.info("userPlan.district: {}", userPlanInfoDto.getDistrict());
        log.info("userPlan.status: {}", userPlanInfoDto.getStatus());

        UserPlan userPlan = userPlanService.createNewUserPlan(userInfo, userPlanInfoDto);

        List<Recommendation> recommendations = recommendationService.sendUserPlanInfo(userPlan, userInfo);
        recommendationService.savePlacesToSession(recommendations);

        model.addAttribute("infoSubmitted", true);

        model.addAttribute("userPlanId", userPlan.getId());
        model.addAttribute("userPlan", userPlanInfoDto);
        model.addAttribute("user", userInfo);

        log.info("userPlan.id: {}", userPlan.getId());
//        return "html/new-user-plan";
        return "redirect:/home";
    }

    @GetMapping("/{userPlanId}")
    public String userPlan(@PathVariable("userPlanId") ObjectId userPlanId, Model model) throws IllegalAccessException {
        UserPlan userPlan = userPlanService.findUserPlanById(userPlanId);
        model.addAttribute("userPlan", userPlan);

//        return "user-plan";
        return "test/userPlan";
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
    @ResponseBody
    public Map<String, Object> savePlacesToUserPlan(@RequestBody List<String> selectedPlaceId,
                                                    Model model, @RequestParam("userPlanId") ObjectId userPlanId,
                                                    UserPlanInfoDto userPlanInfo,
                                                    @AuthenticationPrincipal CustomOAuth2User oAuth2User)
            throws IllegalAccessException {
        User user = userService.findUserByEmail(oAuth2User);
        UserPlan foundUserPlan = userPlanService.findUserPlanById(userPlanId);
        log.info("userPlan.id: {}", userPlanId);

        List<Place> selectedPlaces = new ArrayList<>();

        for (String placeId : selectedPlaceId) {
            Place place = placeService.findByPlaceId(placeId);
            selectedPlaces.add(place);
        }
        model.addAttribute("selectedPlaces", selectedPlaces);
        userPlanService.updateUserPlanPlaces(foundUserPlan, selectedPlaces);

        // JSON으로 변환
        Map<String, Object> response = new HashMap<>();
        response.put("selectedPlaces", selectedPlaces);
        response.put("userPlan", userPlanInfo);

        log.info(selectedPlaces.toString());

        return response;
    }

    @GetMapping("/{userPlanId}/user-plan-info")
    public String updateUserPlanNameAndStatusForm(@PathVariable("userPlanId") ObjectId userPlanId,
                                                  Model model) throws IllegalAccessException {
        UserPlan userPlan=userPlanService.findUserPlanById(userPlanId);
        model.addAttribute("userPlan", userPlan);

        return "test/editUserPlanInfoForm";
    }

    @PostMapping("/{userPlanId}/user-plan-info")
    public String updateUserPlanNameAndStatus(@PathVariable("userPlanId") ObjectId userPlanId,
                                              @ModelAttribute("updateUserPlan") UpdateUserPlanInfoDto userPlanInfo,
                                              @AuthenticationPrincipal CustomOAuth2User oAuth2User) throws IllegalAccessException {
        User user=userService.findUserByEmail(oAuth2User);
        UserPlan userPlan=userPlanService.findUserPlanById(userPlanId);

        userPlanService.updateUserPlanInfo(user, userPlan, userPlanInfo);

        return "redirect:/home";
    }
}
