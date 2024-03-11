package travel.travelapplication.config.auth;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import travel.travelapplication.config.auth.dto.OAuthAttributes;
import travel.travelapplication.config.auth.dto.SessionUser;
import travel.travelapplication.config.auth.jwt.JwtService;
import travel.travelapplication.entity.User;
import travel.travelapplication.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

//    private final HttpSession httpSession;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration().getRegistrationId(); // google

        String userNameAttributeName = userRequest
                .getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());


//        String provider=userRequest.getClientRegistration().getClientId();
//        String providerId=oAuth2User.getAttribute("sub");
//        String username= oAuth2User.getAttribute("name");
//        String email=oAuth2User.getAttribute("email");
        String role="ROLE_USER";

        User user=saveUser(attributes, role); // CustomOAuth2User 만들어야 할 듯
//        System.out.println(oAuth2User.getAttributes());
//        httpSession.setAttribute("user", new SessionUser(user.getName(), user.getEmail()));
//        httpSession.setAttribute("access_token", accessToken);

        return oAuth2User;
    }

//    private User saveUser(OAuthAttributes attributes) { // 수정 필요
//
//        Optional<User> findUser = userRepository.findByEmail(attributes.getEmail());
//        return findUser.orElseGet(() -> userRepository.save(attributes.toEntity()));
//    }

    private User saveUser(OAuthAttributes attributes, String role) {
        User findUser=userRepository.findByEmail(attributes.getEmail())
                .orElse(null);

//        if(findUser.isPresent()) {
//            return findUser.get();
//        } else {
//            User user=new User(attributes.getName(), attributes.getEmail(), role);
//            return userRepository.save(user);
//        }

        if(findUser==null) {
            User user=new User(attributes.getName(), attributes.getEmail(), role);
            return userRepository.save(user);
        } else {
            return findUser;
//            return userRepository.save(findUser);
        }
    }
}
