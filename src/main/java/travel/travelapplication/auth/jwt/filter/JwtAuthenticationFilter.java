package travel.travelapplication.auth.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import travel.travelapplication.auth.jwt.JwtService;
import travel.travelapplication.auth.jwt.util.PasswordUtil;
import travel.travelapplication.user.domain.User;
import travel.travelapplication.user.repository.UserRepository;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_URL="/login";

    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final GrantedAuthoritiesMapper authoritiesMapper=new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException {

        if(req.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(req, resp);
            return;
        }

        // 사용자 요청 헤더에서 refreshToken 추출 -> 유효성 검사
        String refreshToken=jwtService.extractRefreshToken(req)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        if(refreshToken!=null) {
            checkRefreshTokenAndReIssueAccessToken(resp, refreshToken);
            return;
        }
        if(refreshToken==null) {
            checkAccessTokenAndAuthentication(req, resp, filterChain);
        }
    }

    // accessToken 재발급
    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse resp, String refreshToken) {
        userRepository.findByRefreshToken(refreshToken)
                .ifPresent(user -> {
                    String reIssuedRefreshToken=reissueRefreshToken(user);
                    jwtService.sendAccessAndRefreshToken(resp, jwtService.createAccessToken(user.getEmail()),
                            reIssuedRefreshToken);
                });
    }

    private String reissueRefreshToken(User user) {
        String reissuedRefreshToken= jwtService.createRefreshToken();
        user.updateRefreshToken(reissuedRefreshToken);
        userRepository.save(user);

        return reissuedRefreshToken;
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest req, HttpServletResponse resp,
                                                  FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(req)
                .filter(jwtService::isTokenValid)
                .ifPresent(accessToken -> jwtService.extractEmail(accessToken)
                        .ifPresent(email -> userRepository.findByEmail(email)
                                .ifPresent(this::saveAuthentication)));

        filterChain.doFilter(req, resp);
    }

    // 인증 허가
    public void saveAuthentication(User user) {
        String password= PasswordUtil.createPassword();

        UserDetails userDetails= org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .password(password)
                .roles(String.valueOf(user.getRole()))
                .build();

        Authentication authentication=new UsernamePasswordAuthenticationToken(userDetails, null,
                authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
