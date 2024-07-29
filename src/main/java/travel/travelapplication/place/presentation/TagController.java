package travel.travelapplication.place.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.dto.tag.TagDtoList;
import travel.travelapplication.place.application.TagService;
import travel.travelapplication.place.domain.Tag;
import travel.travelapplication.user.application.UserService;
import travel.travelapplication.user.domain.User;

import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TagController {

    private final UserService userService;
    private final TagService tagService;

    @ModelAttribute("tags")
    public Map<String, String> tags() {
        return tagService.findAllTag();
    }

    @GetMapping("/tag")
    public String tagForm(Model model) throws IllegalAccessException {
        TagDtoList tagDtoList = new TagDtoList();
        model.addAttribute("tagDtoList", tagDtoList);
        return "tagForm";
    }

    @PostMapping("/tag")
    public String addTag(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
                         @ModelAttribute TagDtoList tagDtoList, Model model) throws IllegalAccessException {
        if(tagDtoList.getTagList().size() < 3) {
            model.addAttribute("errorMessage", "3개 이상의 태그를 선택하세요.");
            return "tagForm";
        }

        User user = userService.findUserByEmail(oAuth2User);

        userService.addTag(user, tagDtoList.getTagList());

        return "redirect:/";
    }

    @GetMapping("/myroom/member/taglist")
    public String userTagList(Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2User) throws IllegalAccessException {
        User user = userService.findUserByEmail(oAuth2User);
        List<Tag> tagList = userService.findAllTag(user);
        model.addAttribute("tagList", tagList);

        return "tags";
    }

    @GetMapping("/register/tag")
    public String registerTag() {
        return "newTag";
    }

}
