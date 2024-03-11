package travel.travelapplication.config.auth.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import travel.travelapplication.config.auth.CustomOAuth2User;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@EnableConfigurationProperties({JwtProperties.class})
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User= (CustomOAuth2User) authentication.getPrincipal();

        // token 생성
        String accessToken=jwtService.createAccessToken(oAuth2User.getEmail());
        String refreshToken= jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(resp, accessToken, refreshToken); // client로 보내기
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
    }
}
