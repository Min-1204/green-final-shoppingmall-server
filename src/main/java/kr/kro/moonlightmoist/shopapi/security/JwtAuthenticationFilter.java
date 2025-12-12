package kr.kro.moonlightmoist.shopapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Spring이 제공하는 필터 기반 클래스
    // 요청당 딱 한 번만 실행을 보장 (중복 실행 방지)
    // doFilterInternal() 메서드 오버라이드

    private final JwtTokenProvider jwtTokenProvider; // JWT 생성 유틸
    private final CustomUserDetailsService customUserDetailsService; // 사용자정보


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            // 요청 헤더에서 토큰 추출
            String jwt = getJwtFromRequest(request);

            // 검증
            if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                // jwt에
                // 토큰에서 로그인아이디 추출
                String loginId = jwtTokenProvider.getLoginIdFromToken(jwt);

                // DB에서 사용자 정보 조회
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginId);

                // Authentication 객체 생성
                // 세션 방식과 동일하게 authentication에 담을 객체를 생성해준다.
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // 요청정보를 Authentication에 추가해야한다.
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

//                SecurityContextHolder에 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("JWT 인증 성공 => 로그인아이디 : {}", loginId);

            }
        } catch(Exception e) {
          log.error("JWT 인증 처리 중 오류 발생 : ", e);
        }

        filterChain.doFilter(request,response);

    }

    // 요청에서 JWT토큰을 꺼내오는 메서드
    private String getJwtFromRequest (HttpServletRequest request) {
        // 해당 Request에서 Header를 가져오는 메서드를 사용해서 Authorization(인가)에 대한 속성을 가져옴
        // JSON 형태로 보내기때문에 해당 Value는 String이고, 그 문자는 곧 Bearer 토큰인 셈.
        String bearerToken = request.getHeader("Authorization"); // 인가 키값

        // StringUtils의 hasText 메서드를 사용해서 bearerToken이 있는지를 검증하고
        // bearerToken에 startsWith 메서드를 사용해서 "Bearer "지정한 문자로 시작하는지를 확인
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            // 해당 조건문이 True 일 때, bearerToen에 subString으로 7번째까지 제거 후 그 뒤의 토큰만 반환하게 만듬.
            // subString은 문자열 특정부분 자르기
            return bearerToken.substring(7); // "Bearer " 제거 후 토큰만 반환
        }
        // false라면 그냥 null 반환
        return null;
    }

}
