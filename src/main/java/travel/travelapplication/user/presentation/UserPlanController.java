package travel.travelapplication.user.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.constant.Status;
import travel.travelapplication.dto.userplan.SelectedPlaceDto;
import travel.travelapplication.dto.userplan.UserPlanDto;
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

    @GetMapping
    public String userPlan() {
        return "html/user-plan";
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

    @GetMapping("/plan-info")
    public String userPlanInfoForm(Model model) {
        model.addAttribute("userPlan", new UserPlanInfoDto());
        return "getPlanInfoForm";
    }

    @PostMapping("/plan-info")
    public String saveUserPlan(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
                               @ModelAttribute("userPlan") UserPlanInfoDto userPlan) throws IllegalAccessException {
        User user = userService.findUserByEmail(oAuth2User);

        log.info("user.name: {}", user.getName());
        log.info("user.email: {}", user.getEmail());

        log.info("userPlan.startDate: {}", userPlan.getStartDate());
        log.info("userPlan.endDate: {}", userPlan.getEndDate());
        log.info("userPlan.budget: {}", userPlan.getBudget());
        log.info("userPlan.city: {}", userPlan.getCity());
        log.info("userPlan.district: {}", userPlan.getDistrict());

        userPlanService.createNewUserPlan(user, userPlan);

        return "redirect:/"; // url 매핑
    }

    @GetMapping("/{userPlanId}")
    public String userPlan(@PathVariable("userPlanId") ObjectId userPlanId, Model model) throws IllegalAccessException {
        UserPlan userPlan = userPlanService.findUserPlanById(userPlanId);
        model.addAttribute("userPlan", userPlan);

        return "user-plan";
    }


}
