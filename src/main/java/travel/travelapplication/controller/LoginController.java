package travel.travelapplication.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class LoginController {

//    @GetMapping("/login")
//    public String login() {
//        return null; // return "login";
//    }


    @GetMapping("/google/login")
    public String googleLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2UserPrincipal) {
        OAuth2User oAuth2User= (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes=oAuth2User.getAttributes();
        System.out.println(attributes);

//        Map<String, Object> attributes1=oAuth2UserPrincipal.getAttributes();

        return attributes.toString();
    }

}
