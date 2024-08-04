package travel.travelapplication.place.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import travel.travelapplication.auth.dto.SessionUser;
import travel.travelapplication.place.application.KakaoMapService;
import travel.travelapplication.place.response.ApiResponse;
import travel.travelapplication.place.response.LocationDto;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/places")
@RequiredArgsConstructor
@Slf4j
public class KakaoMapController {

    private final KakaoMapService kakaoMapService;

    private List<ApiResponse> list;

    @GetMapping("/recommend-details")
    public String getRecommendationDetails(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        List<SessionUser> sessions = (List<SessionUser>) session.getAttribute("recommendation-result");
        Mono<List<ApiResponse>> listMono = kakaoMapService.callKakaoMapApi(sessions);

        list = listMono.block();

        model.addAttribute("list", list);

        return "test/map-data";
    }

    @GetMapping("/map-marker")
    public String showMapWithMarker(Model model) {
        List<LocationDto> locList=new ArrayList<>();

        for(ApiResponse response:list) {
            locList.add(new LocationDto(response.getPlaceName(), response.getY(), response.getX())); // X, Y 방향 유의
        }
        model.addAttribute("locList", locList);

        return "test/map-marker";
    }

}
