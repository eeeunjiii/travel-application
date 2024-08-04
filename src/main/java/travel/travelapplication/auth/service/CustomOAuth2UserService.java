package travel.travelapplication.auth.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.auth.dto.OAuthAttributes;
import travel.travelapplication.auth.dto.SessionUser;
import travel.travelapplication.place.application.RecommendationService;
import travel.travelapplication.place.domain.Recommendation;
import travel.travelapplication.user.domain.User;
import travel.travelapplication.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final RecommendationService recommendationService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        log.info("userRequest: {}", userRequest);
        log.info("userRequest client registration: {}", userRequest.getClientRegistration());
        log.info("access token: {}", userRequest.getAccessToken().getTokenValue());
        log.info("attribute: {}", oAuth2User.getAttributes());
        log.info("OAuth2 로그인 요청 진입");

        CompletableFuture<List<Recommendation>> recommendationFuture = recommendationService.fetchData();
        CompletableFuture<List<Recommendation>> results = recommendationFuture.thenApply(recommendation -> {
            List<Recommendation> list = new ArrayList<>(recommendation);
            return list;
        });

        // 추천 서비스 호출 (Session 저장)
        try {
            List<Recommendation> recommendations = results.get();
            List<SessionUser> sessions = (List<SessionUser>) httpSession.getAttribute("recommendation-result");
            if(sessions == null) {
                sessions = new ArrayList<>();
            }

            for(Recommendation recommendation:recommendations) {
                SessionUser sessionUser = recommendation.toSessionUser();
                sessions.add(sessionUser);
            }
            httpSession.setAttribute("recommendation-result", sessions);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        log.info("registrationId: {}", registrationId);

        String userNameAttributeName = userRequest
                .getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        String role = "ROLE_USER";
        String accessToken = userRequest.getAccessToken().getTokenValue();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        User findUser = saveUser(attributes, role, accessToken);

        return new CustomOAuth2User(Collections.singleton(new SimpleGrantedAuthority(findUser.getRole())),
                oAuth2User.getAttributes(),
                attributes.getNameAttributeKey(),
                findUser.getEmail(),
                findUser.getName(),
                findUser.getRole(),
                registrationId
        );
    }

    private User saveUser(OAuthAttributes attributes, String role, String accessToken) {

        User findUser = getUser(attributes);

        if (findUser == null) {
            User user = User.builder()
                    .name(attributes.getName())
                    .email(attributes.getEmail())
                    .role(role)
                    .accessToken(accessToken)
                    .userPlans(null)
                    .likedPlaces(null)
                    .tags(null)
                    .savedPlans(null)
                    .build();
            userRepository.insert(user);
            return user;
        }
        return findUser;
    }

    private User getUser(OAuthAttributes attributes) {
        return userRepository.findByEmail(attributes.getEmail())
                .orElse(null);
    }
}
