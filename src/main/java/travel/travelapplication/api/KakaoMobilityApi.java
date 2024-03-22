package travel.travelapplication.api;

import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoMobilityApi {

    private final String url="https://apis-navi.kakaomobility.com";

    @Value("${spring.api.kakao-api-key}")
    private final String apiKey;

    private final String startX; // 출발지 X 좌표
    private final String startY; // 출발지 Y 좌표
    private final String startName; // 출발지명

    private final String endX; // 목적지 X 좌표
    private final String endY; // 목적지 Y 좌표
    private final String destName; // 목적지명

    public Mono<JsonObject> getMobilityAPI() {
        WebClient webClient= WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Authorization", apiKey)
                .build();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/directions")
                    .queryParam("origin", startX, startY)
                    .queryParam("destination", endX, endY)
                    .build())
//                .retrieve()
//                .bodyToMono(String.class)
                .exchangeToMono(resp -> {
                    if(resp.statusCode()== HttpStatus.OK) {
                        return resp.bodyToMono(String.class);
                    } else {
                        log.warn("{} API Error: {}", this, resp.statusCode());
                        return Mono.empty();
                    }
                })
//                .block();
                .map(JsonParser::parseString)
                .map(JsonElement::getAsJsonObject);
    }

}
