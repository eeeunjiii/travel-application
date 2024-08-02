package travel.travelapplication.user.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import travel.travelapplication.auth.CustomOAuth2User;

import java.util.Map;

@Controller
@Slf4j
public class HomeController {

    private final ResourceLoader resourceLoader;

    public HomeController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

//    @GetMapping("/home")
//    @ResponseBody
//    public Resource profile() {
//        return resourceLoader.getResource("classpath:/templates/html/home.html");
//    }

    @GetMapping("/home")
    @ResponseBody
    public Resource home(Model model, @AuthenticationPrincipal CustomOAuth2User oAuth2User) {

        if (oAuth2User == null) {
            return resourceLoader.getResource("classpath:/templates/html/home.html");
        }

        String provider = oAuth2User.getRegistrationId();

        if (provider.equals("google")) {
            model.addAttribute("username", oAuth2User.getAttribute("name"));
            model.addAttribute("email", oAuth2User.getAttribute("email"));
            return resourceLoader.getResource("classpath:/templates/html/home.html");
        } else { // naver
            Map<Object, String> response = (Map<Object, String>) oAuth2User.getAttribute("response");
            model.addAttribute("username", response.get("name"));
            model.addAttribute("email", response.get("email"));
            return resourceLoader.getResource("classpath:/templates/html/home.html");
        }
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "test/loginForm";
    }

    @GetMapping("/map-test1")
    public String showMap() {
        return "map-test";
    }

}
