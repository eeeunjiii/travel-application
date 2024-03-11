package travel.travelapplication.config.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travel.travelapplication.entity.User;
import travel.travelapplication.repository.UserRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtService {

    // jwt.yml에서 설정된 값 가져오기
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.expiration}")
    private long accessTokenValidityInSeconds;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenValidityInSeconds;
    @Value("${jwt.access.header}")
    private String accessHeader;
    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    //== 1 ==//
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String USERNAME_CLAIM = "email";
    private static final String BEARER = "Bearer ";

    private final UserRepository userRepository;

    public String createAccessToken(String email) {
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis()+accessTokenValidityInSeconds))
                .withClaim(USERNAME_CLAIM, email)
                .sign(Algorithm.HMAC512(secret));
    }

    public String createRefreshToken() {
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis()+refreshTokenValidityInSeconds))
                .sign(Algorithm.HMAC512(secret));
    }

    public void updateRefreshToken(String email, String refreshToken) {
        userRepository.findByEmail(email)
                .ifPresentOrElse(user -> user.updateRefreshToken(refreshToken),
                        () -> new Exception("회원 조회 실패")
                );
    }


    public void destroyRefreshToken(String email) {
        userRepository.findByEmail(email)
                .ifPresentOrElse(
                        User::destroyRefreshToken,
                        () -> new Exception("회원 조회 실패")
                );
    }

    public void sendAccessAndRefreshToken(HttpServletResponse resp, String accessToken, String refreshToken) {
        resp.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(resp, accessToken);
        setRefreshTokenHeader(resp, refreshToken);

        Map<String, String> tokenMap=new HashMap<>();
        tokenMap.put(ACCESS_TOKEN_SUBJECT, accessToken);
        tokenMap.put(REFRESH_TOKEN_SUBJECT, refreshToken);
    }

    public void sendAccessToken(HttpServletResponse resp, String accessToken) { // 삭제
        resp.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(resp, accessToken);

        Map<String, String> tokenMap=new HashMap<>();
        tokenMap.put(ACCESS_TOKEN_SUBJECT, accessToken);
    }

    public Optional<String> extractAccessToken(HttpServletRequest req) {
        return Optional.ofNullable(req.getHeader(accessHeader)).filter(
                accessToken -> accessToken.startsWith(BEARER)
        ).map(accessToken -> accessToken.replace(BEARER, ""));
    }

    public Optional<String> extractRefreshToken(HttpServletRequest req) {
        return Optional.ofNullable(req.getHeader(refreshHeader)).filter(
                refreshToken -> refreshToken.startsWith(BEARER)
        ).map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public Optional<String> extractEmail(String accessToken) {
        try {
            return Optional.ofNullable(
                    JWT.require(Algorithm.HMAC512(secret)).build().verify(accessToken)
                            .getClaim(USERNAME_CLAIM)
                            .asString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public void setAccessTokenHeader(HttpServletResponse resp, String accessToken) {
        resp.setHeader(accessHeader, accessToken);
    }

    public void setRefreshTokenHeader(HttpServletResponse resp, String refreshToken) {
        resp.setHeader(refreshHeader, refreshToken);
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secret)).build().verify(token);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
