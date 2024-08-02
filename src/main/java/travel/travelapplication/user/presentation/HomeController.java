package travel.travelapplication.user.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.user.application.UserService;
import travel.travelapplication.user.domain.User;


@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2User) throws IllegalAccessException {
        if(oAuth2User==null) {
            return "home";
        }
        User user = userService.findUserByEmail(oAuth2User);

        model.addAttribute("user", user);

        return "home";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/map-test1")
    public String showMap() {
        return "map-test";
    }

}
