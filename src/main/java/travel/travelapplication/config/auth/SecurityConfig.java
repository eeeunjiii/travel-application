package travel.travelapplication.config.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import travel.travelapplication.config.auth.jwt.OAuth2SuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, OAuth2SuccessHandler oAuth2SuccessHandler) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // 예외 처리 핸들러 추가해야 함
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers((headerConfig) ->
                        headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/home", "/login/**").permitAll()
                                .anyRequest().authenticated()
                )
//                .exceptionHandling((exceptionConfig) ->
//                        exceptionConfig.authenticationEntryPoint(unauthorizedEntryPoint).accessDeniedHandler(accessDeniedHandler)
//                )
                .oauth2Login((oauth2Login) ->
                        oauth2Login
                                .loginPage("/login")
                                .defaultSuccessUrl("/home")
                                .failureUrl("/login")
                                .userInfoEndpoint((userInfo) ->
                                        userInfo
                                                .userService(customOAuth2UserService))
                                .successHandler(oAuth2SuccessHandler)
                )
                .logout((logout) ->
                        logout.logoutUrl("/logout") // logout url 다시 확인
                                .logoutSuccessUrl("/home")
                                .invalidateHttpSession(true)
                );


        return http.build();
    }

    /*private final AuthenticationEntryPoint unauthorizedEntryPoint =
            ((request, response, authException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security unauthorized...");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer=response.getWriter();
                writer.write(json);
                writer.flush();
            });

    private final AccessDeniedHandler accessDeniedHandler =
            ((request, response, accessDeniedException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring security forbidden...");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            });*/

//    @Getter
//    @ToString
//    @RequiredArgsConstructor
//    public class ErrorResponse {
//        private final HttpStatus status;
//        private final String message;
//    }
}
