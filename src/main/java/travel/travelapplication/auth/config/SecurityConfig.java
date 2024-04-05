package travel.travelapplication.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import travel.travelapplication.auth.jwt.filter.JwtAuthenticationFilter;
import travel.travelapplication.auth.handler.OAuth2FailureHandler;
import travel.travelapplication.auth.jwt.JwtService;
import travel.travelapplication.auth.service.CustomOAuth2UserService;
import travel.travelapplication.auth.handler.OAuth2SuccessHandler;
import travel.travelapplication.user.repository.UserRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // 예외 처리 핸들러 추가해야 함
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers((headerConfig) ->
                        headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers( "/login", "/home", "/", "/h2-console/**").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login((oauth2Login) ->
                        oauth2Login
                                .userInfoEndpoint((userInfo) ->
                                        userInfo
                                                .userService(customOAuth2UserService))
                                .successHandler(oAuth2SuccessHandler)
                                .failureHandler(oAuth2FailureHandler)
                                .defaultSuccessUrl("/home")
                )
                .logout((logout) ->
                        logout
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutUrl("/logout") // logout url 다시 확인
                                .logoutSuccessUrl("/home")
                );

        http.addFilterAfter(jwtAuthenticationFilter(), LogoutFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService, userRepository);
    }
}
