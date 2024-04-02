package travel.travelapplication.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travel.travelapplication.repository.UserRepository;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
@Slf4j
public class JwtService {

    // jwt.yml에서 설정된 값 가져오기
    @Value("${jwt.secretKey}")
    private String secret;

    @Value("${jwt.access.expiration}")
    private long accessTokenValidityInSeconds;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenValidityInSeconds;
    @Value("${jwt.access.header}")
    private String accessHeader;
    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String USERNAME_CLAIM = "email";
    private static final String BEARER = "Bearer ";

    private final UserRepository userRepository;

    public String createAccessToken(String email) {
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis()+accessTokenValidityInSeconds*1000))
                .withClaim(USERNAME_CLAIM, email)
                .sign(Algorithm.HMAC512(secret));
    }

    public String createRefreshToken() {
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis()+refreshTokenValidityInSeconds))
                .sign(Algorithm.HMAC512(secret));
    }

    public void updateRefreshToken(String email, String refreshToken){
        userRepository.findByEmail(email)
                .ifPresentOrElse(user -> user.updateRefreshToken(refreshToken),
                        () -> new Exception("회원 조회 실패")
                );
    }

    // response.header를 통해 token 전송
    public void sendAccessAndRefreshToken(HttpServletResponse resp, String accessToken, String refreshToken) {
        resp.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(resp, accessToken);
        setRefreshTokenHeader(resp, refreshToken);
        log.info("access token, refresh token 헤더 설정 완료");
    }

    // request.header를 통해 전달받은 토큰 추출
    public Optional<String> extractAccessToken(HttpServletRequest req) {
        return Optional.ofNullable(req.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith(BEARER)
        ).map(accessToken -> accessToken.replace(BEARER, ""));
    }

    public Optional<String> extractRefreshToken(HttpServletRequest req) {
        return Optional.ofNullable(req.getHeader(refreshHeader)).filter(
                refreshToken -> refreshToken.startsWith(BEARER)
        ).map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    // 토큰에 포함된 값 확인
    public Optional<String> extractEmail(String accessToken) {
        try {
            return Optional.ofNullable(
                    JWT.require(Algorithm.HMAC512(secret))
                            .build()
                            .verify(accessToken)
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
            JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
