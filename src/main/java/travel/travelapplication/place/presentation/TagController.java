package travel.travelapplication.place.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.dto.tag.TagDto;
import travel.travelapplication.place.domain.Tag;
import travel.travelapplication.user.application.UserService;
import travel.travelapplication.user.domain.User;

import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TagController {

    private final UserService userService;

    @ModelAttribute("tagList")
    public List<Tag> tags() {
        List<Tag> tags=new ArrayList<>();
        tags.add(new Tag("KEYWORD1"));
        tags.add(new Tag("KEYWORD2"));
        tags.add(new Tag("KEYWORD3"));
        tags.add(new Tag("KEYWORD4"));
        return tags;
    }

    @GetMapping("/tag")
    public String tagForm(Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2User) throws IllegalAccessException {
        User user=userService.findUserByEmail(oAuth2User);
        model.addAttribute("user", user);

        return "tag";
    }

    @PostMapping("/tag")
    @ResponseBody
    public String addTag(@ModelAttribute User user,
                         @RequestParam("tags") List<String> selectedTags) throws IllegalAccessException {
        List<Tag> userTag=new ArrayList<>();

        for(String tagName: selectedTags) {
            Tag tag=new Tag(tagName);
            userTag.add(tag);
        }
        userService.addTag(user, userTag);
        userService.save(user);

        log.info("user.name: {}, user.tags: {}", user.getName(), user.getTags());

        return "ok";
    }

    @GetMapping("/myroom/member/taglist")
    public String userTagList(Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2User)
            throws IllegalAccessException {
        User user = userService.findUserByEmail(oAuth2User);
        List<Tag> tagList = userService.findAllTag(user);
        model.addAttribute("tagList", tagList);

        return null; // return "tagList";
    }

}
