package travel.travelapplication.user.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.user.application.UserPlanService;
import travel.travelapplication.user.application.UserService;
import travel.travelapplication.user.domain.User;
import travel.travelapplication.user.domain.UserPlan;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/myroom")
public class UserController {

    private final UserService userService;
    private final UserPlanService userPlanService;
    private final ResourceLoader resourceLoader;

    @GetMapping("/profile")
    @ResponseBody
    public Resource profile() {
        return resourceLoader.getResource("classpath:/templates/html/profile.html");
    }

    @GetMapping("/profile/username")
    public String updateUsernameForm(Model model,
                                     @AuthenticationPrincipal CustomOAuth2User oAuth2User)
            throws IllegalAccessException {
        User user = userService.findUserByEmail(oAuth2User);

        model.addAttribute("oldName", user.getName());
        model.addAttribute("userEmail", user.getEmail());

        return "editUsername";
    }

    @PostMapping("/profile/username")
    public String updateUserName(@RequestParam("editedName") String newName,
                                 @RequestParam("email") String email)
            throws IllegalAccessException {

        log.info("user email: {}", email);

        userService.updateUserName(email, newName);

        log.info("new username: {}", newName);

        return "redirect:/";
    }

    @GetMapping("/user-plans")
    public String userPlans(Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2User)
            throws IllegalAccessException {
        User user = userService.findUserByEmail(oAuth2User);
        List<UserPlan> userPlans = userPlanService.findAllUserPlan(user);

        model.addAttribute("userPlans", userPlans);

        return "userPlans";
    }
}
