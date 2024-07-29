package travel.travelapplication.place.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import travel.travelapplication.place.application.KakaoMapService;
import travel.travelapplication.place.response.ApiResponse;
import travel.travelapplication.place.response.LocationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/places")
@RequiredArgsConstructor
@Slf4j
public class KakaoMapController {

    private final KakaoMapService kakaoMapService;

    private List<ApiResponse> list;

    @PostMapping("/get-result")
    @ResponseBody
    public String getRecommendationResult(@RequestBody List<Map<String, Object>> datas,
                                          HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> places = (ArrayList) session.getAttribute("recommendation-result");

        Mono<List<ApiResponse>> listMono = kakaoMapService.callKakaoMapApi(places);
        list = listMono.block();

        return "ok";
    }

    @GetMapping("/recommend-details")
    public String getRecommendationDetails(Model model) {
        model.addAttribute("list", list);

        return "map-data";
    }

    @GetMapping("/map-marker")
    public String showMapWithMarker(Model model) {
        List<LocationDto> locList=new ArrayList<>();

        for(ApiResponse response:list) {
            locList.add(new LocationDto(response.getPlaceName(), response.getY(), response.getX())); // X, Y 방향 유의
        }

        log.info("locList: {}", locList.get(0).getTitle());
        model.addAttribute("locList", locList);

        return "map-marker";
    }

}
