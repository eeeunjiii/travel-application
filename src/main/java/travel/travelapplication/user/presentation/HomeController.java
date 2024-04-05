package travel.travelapplication.user.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import travel.travelapplication.auth.CustomOAuth2User;

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

    @RequestMapping(value = "/home")
    public String index() {
//        log.info("index.html");
        return "index";
    }
}
