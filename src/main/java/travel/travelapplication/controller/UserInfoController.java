package travel.travelapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService userService;

    @PutMapping("/profile/username")
    public String updateUserName(@ModelAttribute Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2User)
            throws IllegalAccessException {
        String email=oAuth2User.getAttribute("email");
        String username= (String) model.getAttribute("username");

        userService.updateUserName(email, username);

        return null; // TODO: 수정 필요
    }
}
