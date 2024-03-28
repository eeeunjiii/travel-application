package travel.travelapplication.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.constant.Status;
import travel.travelapplication.dto.userplan.SelectedPlaceDto;
import travel.travelapplication.entity.UserPlan;
import travel.travelapplication.service.UserPlanService;

import static travel.travelapplication.dto.userplan.UserPlanDto.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserPlanController {

    private final UserPlanService userPlanService;

    @GetMapping("/plans/{userId}")
    public String userPlanInfoForm(@PathVariable("userId")ObjectId userId) {
        return null; // UserPlan 정보를 등록하는 폼 뷰 반환
    }

    @PostMapping("/plans/{userId}") // TODO: userId 넘어오는 부분 수정
    public String saveUserPlan(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
                               @RequestBody SelectedPlaceDto selectedPlaceDto,
                               UserPlanInfoDto userPlanInfoDto) {
        String email= oAuth2User.getEmail();
        userPlanService.saveUserPlan(email, selectedPlaceDto, userPlanInfoDto);

        return "redirect:/"; // TODO: 선택한 place 받아오는 부분 수정 필요
    }

    // TODO: update userplan info URI

    @PostMapping("/plans")
    public String shareUserPlan(@ModelAttribute("userplan") UserPlan userPlan) {
        if(isPublic(userPlan)) {
            userPlanService.shareUserPlan(userPlan);
        }
        return "redirect:/";
    }

    private boolean isPublic(UserPlan userPlan) { // userPlan이 public인지 확인
        if(userPlan.getStatus()==Status.PUBLIC) return true;
        return false;
    }

}
