package travel.travelapplication.user.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//import travel.travelapplication.auth.CustomOAuth2User;

import java.util.Optional;

@Controller
@Slf4j
public class HomeController {

//    @GetMapping("/home")
//    public String home(Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2UserPrincipal) {
//        if(oAuth2UserPrincipal != null) {
//            model.addAttribute("username", oAuth2UserPrincipal.getName());
//        }
//        return "index"; // return "home";
//    }

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication,
                                          @AuthenticationPrincipal OAuth2User oAuth2) {
        log.info("/test/login: {}", authentication.getPrincipal());
        OAuth2User oAuth2User=(OAuth2User) authentication.getPrincipal();
        log.info("authentication: {}", oAuth2User.getAttributes());
        log.info("email: {}", Optional.ofNullable(oAuth2User.getAttribute("email")));
        log.info("name: {}", Optional.ofNullable(oAuth2User.getAttribute("name")));
        log.info("oauth2: {}", oAuth2.getAttributes());
        log.info("oauth2 email: {}", Optional.ofNullable(oAuth2.getAttribute("email")));
        return "세션 정보 확인하기";
    }

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    @ResponseBody
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user() {
        return "user";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "index";
    }
}
