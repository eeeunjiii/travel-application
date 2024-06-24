package travel.travelapplication.place.presentation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import travel.travelapplication.place.application.KakaoMapService;
import travel.travelapplication.place.application.PlaceService;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.place.response.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Controller
@RequiredArgsConstructor
@RequestMapping("/places")
@Slf4j
public class PlaceController {
    private final PlaceService placeService;
    private final KakaoMapService kakaoMapService;

    private List<ApiResponse> list;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Place> findPlaceById(@RequestParam(name = "id") ObjectId id) {
        Place place = placeService.findById(id);
        return ResponseEntity.ok(place);
    }

    @GetMapping("/{name}")
    @ResponseBody
    public ResponseEntity<Place> findPlaceByName(@RequestParam(name = "name") String name) {
        Place place = placeService.findByName(name);
        return ResponseEntity.ok(place);
    }

    @ResponseBody
    @GetMapping("/user-id-data")
    public Long sendUserId() {
        return 112L; // 로그인한 사용자의 user_id 반환하기
    }

    @PostMapping("/get-result")
    @ResponseBody
    public String getRecommendationResult(@RequestBody List<Map<String, Object>> datas) {
        List<String> places = new ArrayList<>();

        for(Map<String, Object> data : datas) {
            log.info("destination name: {}", data.get("name"));
            places.add((String) data.get("name"));
        }

        Mono<List<ApiResponse>> listMono = kakaoMapService.callKakaoMapApi(places);
        list = listMono.block();

        return "ok";
    }

    @GetMapping("/recommend-details")
    public String getRecommendationDetails(Model model) {
        model.addAttribute("list", list);

        return "map-data";
    }
}
