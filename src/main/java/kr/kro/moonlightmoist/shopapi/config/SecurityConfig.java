package kr.kro.moonlightmoist.shopapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 스프링부트가 시작할 때 해당 클래스를 스캔 그안의 설정들을 읽어서 적용
@EnableWebSecurity
public class SecurityConfig {

    @Bean // "이 메소드가 반환하는 객체를 스프링이 관리해줘"
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 공격 방지 설정 disable() 끄는 메서드
                .csrf(csrf -> csrf.disable())
                // 어떤 요청을 허용하고, 막을지를 설정하는 메서드
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/login").permitAll()
                        .requestMatchers("/api/user/signup").permitAll()
                        .requestMatchers("/api/user/check-loginId").permitAll()

                        .requestMatchers("/api/user/**").hasRole("USER")
                        .anyRequest().authenticated());

//            anyRequest() 모든요청을 의미, premitAll() 모든요청 허용
        return http.build(); // 해당 http를 만들어서 반환
    }
}
