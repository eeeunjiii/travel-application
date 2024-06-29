package travel.travelapplication.user.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import travel.travelapplication.auth.CustomOAuth2User;

import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2User) {

        if(oAuth2User==null) {
            return "home";
        }

        String provider= oAuth2User.getRegistrationId();

        if(provider.equals("google")) {
            model.addAttribute("username", oAuth2User.getAttribute("name"));
            model.addAttribute("email", oAuth2User.getAttribute("email"));
            return "home";
        } else { // naver
            Map<Object, String> response=(Map<Object, String>) oAuth2User.getAttribute("response");
            model.addAttribute("username", response.get("name"));
            model.addAttribute("email", response.get("email"));
            return "home";
        }
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
