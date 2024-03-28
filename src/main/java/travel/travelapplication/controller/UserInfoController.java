package travel.travelapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.dto.user.UserDto;
import travel.travelapplication.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService userService;

    @GetMapping("/profile/username")
    public String updateUsernameForm() {
        return null; // update username form을 반환하는 뷰
    }

    @PutMapping("/profile/username")
    public String updateUserName(@ModelAttribute UserDto userDto, @AuthenticationPrincipal CustomOAuth2User oAuth2User)
            throws IllegalAccessException {
        String email=oAuth2User.getAttribute("email");
        String username= userDto.getUsername();

        userService.updateUserName(email, username);

        return "redirect:/";
    }


}
