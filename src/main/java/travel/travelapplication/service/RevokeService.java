package travel.travelapplication.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import travel.travelapplication.auth.jwt.JwtService;
import travel.travelapplication.entity.User;
import travel.travelapplication.repository.UserRepository;

import java.util.Optional;

@Service
@Slf4j
public class RevokeService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final String naverClientId;
    private final String naverClientSecret;

    @Autowired
    public RevokeService(UserRepository userRepository, JwtService jwtService,
                         @Value("${spring.security.oauth2.client.registration.naver.client-id}") String naverClientId,
                         @Value("${spring.security.oauth2.client.registration.naver.client-secret}") String naverClientSecret) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.naverClientId = naverClientId;
        this.naverClientSecret = naverClientSecret;
    }

    public void deleteGoogleAccount(String accessToken) {
        User user=extractUserFromAccessToken(accessToken);
        String token=user.getAccessToken();
        deleteUser(user);

        sendRevokeRequest("google", token);
    }

    public void deleteNaverAccount(String accessToken) {
        User user=extractUserFromAccessToken(accessToken);
        String token=user.getAccessToken();
        deleteUser(user);

        sendRevokeRequest("naver", token);
    }

    private void deleteUser(User user) {
        userRepository.delete(user);
    }

    private User extractUserFromAccessToken(String accessToken) {
        Optional<String> email=jwtService.extractEmail(accessToken);

        if(email.isEmpty()) {
            throw new IllegalArgumentException("회원 조회 실패");
        }
        Optional<User> user=userRepository.findByEmail(email.get());
        if(user.isEmpty()) {
            throw new IllegalArgumentException("회원 조회 실패");
        }
        return user.get();
    }

    private void sendRevokeRequest(String provider, String accessToken) {
        String naverRevokeURL="https://nid.naver.com/oauth2.0/token";
        String googleRevokeURL="https://accounts.google.com/o/oauth2/revoke";

        if(provider.equals("google")) {
            WebClient webClient=WebClient.create();

            String response=webClient.get()
                    .uri(uriBuilder -> uriBuilder.path(googleRevokeURL)
                            .queryParam("token", accessToken)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("response: {}", response);

        } else if(provider.equals("naver")) {
            WebClient webClient=WebClient.create();

            String response=webClient.get()
                    .uri(uriBuilder -> uriBuilder.path(naverRevokeURL)
                            .queryParam("client_id", naverClientId)
                            .queryParam("client_secret", naverClientSecret)
                            .queryParam("access_token", accessToken)
                            .queryParam("service_provider", "NAVER")
                            .queryParam("grant_type", "delete")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("response: {}", response);
        }

    }
}
