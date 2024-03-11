package travel.travelapplication.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OAuth2User oAuth2UserPrincipal) {
        if(oAuth2UserPrincipal != null) {
            model.addAttribute("username", oAuth2UserPrincipal.getName());
        }
        return null; // return "home";
    }
}
