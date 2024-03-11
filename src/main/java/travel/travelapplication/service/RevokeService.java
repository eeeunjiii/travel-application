package travel.travelapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import travel.travelapplication.config.auth.jwt.JwtService;
import travel.travelapplication.entity.User;
import travel.travelapplication.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RevokeService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    public void deleteGoogleAccount(String accessToken) {
        User user=extractUserFromAccessToken(accessToken);
        deleteUser(user);

        String data="token=" +user.getAccessToken();
        sendRevokeRequest(data, "google");
    }

    public void deleteNaverAccount(String accessToken) {
        User user=extractUserFromAccessToken(accessToken);
        deleteUser(user);

        String data="client_id="+naverClientId+
                "&client_secret="+naverClientSecret+
                "&access_token="+user.getAccessToken()+
                "&service_provider=NAVER"+
                "&grant_type=delete";

        sendRevokeRequest(data, "naver");
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

    private void sendRevokeRequest(String data, String provider) {
        String naverRevokeURL="https://nid.naver.com/oauth2.0/token";
        String googleRevokeURL="https://accounts.google.com/o/oauth2/revoke";

        RestTemplate restTemplate=new RestTemplate();
        String revokeURL="";

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity=new HttpEntity<>(data, headers);

        switch (provider) {
            case "google" -> revokeURL=googleRevokeURL;
            case "naver" -> revokeURL=naverRevokeURL;
        }

        ResponseEntity<String> responseEntity=restTemplate.exchange(revokeURL, HttpMethod.POST, entity, String.class);

        HttpStatus httpStatus= (HttpStatus) responseEntity.getStatusCode();
        String responseBody=responseEntity.getBody();
    }
}
