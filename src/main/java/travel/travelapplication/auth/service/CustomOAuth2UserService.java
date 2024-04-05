package travel.travelapplication.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.auth.dto.OAuthAttributes;
import travel.travelapplication.constant.Role;
import travel.travelapplication.user.domain.User;
import travel.travelapplication.user.repository.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

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

        String oAuth2AccessToken=userRequest.getAccessToken().getTokenValue();

        Role role=Role.USER;

        User user=saveUser(attributes, role, oAuth2AccessToken);

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString())),
                oAuth2User.getAttributes(),
                attributes.getNameAttributeKey(),
                user.getEmail(),
                user.getRole()
        );
    }

    private User saveUser(OAuthAttributes attributes, Role role, String accessToken) {
        User findUser=userRepository.findByEmail(attributes.getEmail())
                .orElse(null);

        if(findUser==null) {
            User user=attributes.toEntity(role, accessToken);
            return userRepository.save(user);
        }
        findUser.updateAccessToken(accessToken);
        return findUser;
    }
}
