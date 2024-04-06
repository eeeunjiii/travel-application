package travel.travelapplication.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import travel.travelapplication.auth.CustomOAuth2User;
import travel.travelapplication.auth.jwt.JwtService;

import java.io.IOException;

// TODO: TOKEN 유효성 검사 코드 추가하기

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final String PREFIX="Bearer ";

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User= (CustomOAuth2User) authentication.getPrincipal();
        String email= oAuth2User.getEmail();

        // token 생성
        String accessToken=jwtService.createAccessToken(oAuth2User.getEmail());
        String refreshToken= jwtService.createRefreshToken();

        resp.addHeader(jwtService.getAccessHeader(), PREFIX+accessToken);
        resp.addHeader(jwtService.getRefreshHeader(), PREFIX+refreshToken);

        jwtService.sendAccessAndRefreshToken(resp, accessToken, refreshToken);
        jwtService.updateRefreshToken(email, refreshToken);
    }
}
