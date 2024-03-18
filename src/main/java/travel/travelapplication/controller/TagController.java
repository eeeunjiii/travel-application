package travel.travelapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.entity.Tag;
import travel.travelapplication.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TagController {

    private final UserService userService;

    @PostMapping("/tag")
    public String addUserTag(@ModelAttribute Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2User)
            throws IllegalAccessException {
        String email=oAuth2User.getAttribute("email");
        userService.addTag(email, (Tag)(model.getAttribute("tag"))); // TODO: 수정 필요

        return null; // return "tag";
    }

    @GetMapping("/myroom/member/taglist")
    public String UserTagList(@ModelAttribute Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2User)
            throws IllegalAccessException {
        String email=oAuth2User.getAttribute("email");
        List<Tag> tagList = userService.findAllTag(email);
        model.addAttribute("tagList", tagList);

        return null; // return "tagList";
    }
}
