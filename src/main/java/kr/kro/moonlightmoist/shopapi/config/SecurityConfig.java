package kr.kro.moonlightmoist.shopapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration // 스프링부트가 시작할 때 해당 클래스를 스캔 그안의 설정들을 읽어서 적용
@EnableWebSecurity
public class SecurityConfig {

    @Bean // "이 메소드가 반환하는 객체를 스프링이 관리해줘"
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 공격 방지 설정 disable() 끄는 메서드
                .csrf(csrf -> csrf.disable())

                // CORS 설정
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 세션 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)// 필요시 세션 생성
                        .maximumSessions(1) // 동시 로그인 설정
                        .maxSessionsPreventsLogin(false) // 새 로그인이 기존 세션 만료
                )

                // 어떤 요청을 허용하고, 막을지를 설정하는 메서드
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/login").permitAll()
                        .requestMatchers("/api/user/signup").permitAll()
                        .requestMatchers("/api/user/check-loginId").permitAll()
                        .requestMatchers("/api/categories", "/api/products/**").permitAll()

                        .requestMatchers("/api/user/**").authenticated()
                        .anyRequest().authenticated());
//            anyRequest() 모든요청을 의미, premitAll() 모든요청 허용
        return http.build(); // 해당 http를 만들어서 반환
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS")); // 허용 메서드
        configuration.setAllowCredentials(true); // 세션 쿠키 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
