package travel.travelapplication.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import travel.travelapplication.auth.CustomOAuth2User;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2UserPrincipal) {
        if(oAuth2UserPrincipal != null) {
            model.addAttribute("username", oAuth2UserPrincipal.getName());
        }
        return null; // return "home";
    }
}
