package travel.travelapplication.user.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.dto.user.UserDto;
import travel.travelapplication.user.application.UserService;
import travel.travelapplication.user.domain.User;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/profile/username")
    public String updateUsernameForm(Model model,
                                     @AuthenticationPrincipal CustomOAuth2User oAuth2User) throws IllegalAccessException {
        User user = userService.findUserByEmail(oAuth2User);

        model.addAttribute("oldName", user.getName());
        model.addAttribute("userEmail", user.getEmail());

        return "editUsername";
    }

    @PostMapping("/profile/username")
//    @ResponseBody
    public String updateUserName(@RequestParam("editedName") String newName,
                                 @RequestParam("email") String email)
            throws IllegalAccessException {

        log.info("user email: {}", email);

        userService.updateUserName(email, newName);

        log.info("new username: {}", newName);

        return "redirect:/";
    }
}
