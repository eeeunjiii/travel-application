package travel.travelapplication.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import travel.travelapplication.auth.jwt.filter.JwtAuthenticationFilter;
import travel.travelapplication.auth.handler.OAuth2FailureHandler;
import travel.travelapplication.auth.jwt.JwtService;
import travel.travelapplication.auth.service.CustomOAuth2UserService;
import travel.travelapplication.auth.handler.OAuth2SuccessHandler;
import travel.travelapplication.repository.UserRepository;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;// TODO; userReposioty dependency injection error
    private final UserRepository userRepository; // TODO: mongoDBRepository @EnableMongoRepositories error 이 문제를 해결해야 다른 문제도 해결될 듯
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
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests // TODO: 경로 다시 설정
                                .requestMatchers("/", "/home", "/login/**").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login((oauth2Login) ->
                        oauth2Login
                                .loginPage("/login")
                                .defaultSuccessUrl("/home")
                                .failureUrl("/login")
                                .userInfoEndpoint((userInfo) ->
                                        userInfo
                                                .userService(customOAuth2UserService))
                                .successHandler(oAuth2SuccessHandler)
                                .failureHandler(oAuth2FailureHandler)
                )
                .logout((logout) ->
                        logout.logoutUrl("/logout") // logout url 다시 확인
                                .logoutSuccessUrl("/home")
                                .invalidateHttpSession(true)
                );

        http.addFilterAfter(jwtAuthenticationFilter(), LogoutFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService, userRepository);
    }
}
