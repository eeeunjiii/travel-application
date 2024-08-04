package travel.travelapplication.place.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import travel.travelapplication.auth.dto.SessionUser;
import travel.travelapplication.place.repository.PlaceRepository;
import travel.travelapplication.place.response.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class KakaoMapService {

    private final PlaceRepository placeRepository;

    private final String REST_API_KEY="c8216cd97b713947e2c2b06151d01b03";

    private final WebClient webClient;

    public KakaoMapService(PlaceRepository placeRepository, WebClient.Builder webClientBuilder) {
        this.placeRepository = placeRepository;

        this.webClient = webClientBuilder
                .baseUrl("https://dapi.kakao.com")
                .defaultHeader("Authorization", "KakaoAK "+REST_API_KEY)
                .build();
    }

    public Mono<List<ApiResponse>> callKakaoMapApi(List<SessionUser> sessions) {
        List<String> places = new ArrayList<>();

        for(SessionUser session : sessions) {
            String place = session.getName();
            places.add(place);
        }
        Flux<String> placeData = Flux.fromIterable(places);
        Mono<List<ApiResponse>> searchResult = getMapSearchResult(placeData);
        return searchResult;
    }

    private Mono<List<ApiResponse>> getMapSearchResult(Flux<String> result) {
        return result.flatMap(query -> callApi(webClient, query)
                .flatMapMany(Flux::fromIterable))
                .collectList();
    }

    private Mono<List<ApiResponse>> callApi(WebClient webClient, String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/local/search/keyword.json")
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .flatMap(
                        responseMap -> {
                            List<Map<String, String>> documents = (List<Map<String, String>>) responseMap.get("documents");
                            List<ApiResponse> list = new ArrayList<>(documents.size());

                            for (Map<String, String> document : documents) {
                                String id = document.get("id");
                                String placeName = document.get("place_name");
                                String phone = document.get("phone");
                                String link = "https://map.kakao.com/link/to/" + id;
                                String address = document.get("road_address_name");
                                String x = document.get("x");
                                String y = document.get("y");

                                ApiResponse response = new ApiResponse(id, placeName, phone, link, address, x, y);

                                list.add(response);
                            }
                            return Mono.just(list);
                        }
                );
    }
}
