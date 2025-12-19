package kr.kro.moonlightmoist.shopapi.user.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.moonlightmoist.shopapi.security.CustomUserDetails;
import kr.kro.moonlightmoist.shopapi.security.JwtTokenProvider;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.dto.*;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import kr.kro.moonlightmoist.shopapi.user.service.UserService;
import kr.kro.moonlightmoist.shopapi.user.service.UserWithdrawalService;
import kr.kro.moonlightmoist.shopapi.usercoupon.service.UserCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor // Final 생성
@RequestMapping("/api/user") // 해당 컨트롤러가 받을 경로
@Slf4j
//@CrossOrigin(origins = "http://localhost:5137")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserWithdrawalService userWithdrawalService;
    private final AuthenticationManager authenticationManager; // 12-10 추가
    private final JwtTokenProvider jwtTokenProvider; // 12-12 추가
    private final UserCouponService userCouponService;

    @PostMapping("/signup") // RequestMapping + ??
    public ResponseEntity<Map<String,Object>> userResister(@RequestBody UserSignUpRequest userSignUpRequest) {
        // @RequestBody JSON 데이터를 Java 객체로 자동 변환해주는 어노테이션
        User registeredUser = userRepository.save(userService.registerUser(userSignUpRequest));
        Long registeredCouponUser = userCouponService.issue(registeredUser.getId(), 1L);
        log.info("회원가입 컨트롤러 신규쿠폰 유저 등록완료 : {} ", registeredCouponUser);
        log.info("회원가입 컨트롤러 신규쿠폰 등록완료된 유저는 : {} ", registeredUser.getLoginId());
        System.out.println("======================================================================");
        log.info("유저정보 Controller => {}"  ,userSignUpRequest);
        log.info("DB에서 꺼낸 저장된 정보 => {}"  ,registeredUser);
        Map<String,Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "회원가입이 완료되었습니다.");
        response.put("coupon","💕신규쿠폰이 발급되었습니다💕");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> Login (@RequestBody UserLoginRequest userLoginRequest,
                                                      HttpServletResponse httpServletResponse) {
        log.info("로그인 요청 : {}", userLoginRequest.getLoginId());

//        Authentication은 스프링 시큐리티에서 **'인증(Authentication)에 대한 모든 정보'**를 담는 최상위 개념의 인터페이스
//        authenticate( 인증을 시작하는 핵심 메서드 ) 인증요청 객체를 받아서 해당 요청이 유효한지 확인하고 인증된 객체를 반환해준다.
//        인터페이스는 두 가지 상태를 표현하기 위해 사용된다. 1. 인증요청 2. 인증완료

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequest.getLoginId(),
                            userLoginRequest.getPassword()
                    )
            );

//        SecurityContext에 저장하기.
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwtToken = jwtTokenProvider.generateToken(authentication);

//        사용자 정보
//        Authentication 인터페이스의 정의상 getPrincipal() 메서드의 반환 타입은 가장 일반적인 타입인 Object 이다.
//        이유는 getPrincipal()에 들어갈 수 있는 객체의 종류가 매우 다양하기 때문. ID 문자열일 수도 있고,
//        OAuth2 토큰일 수도 있으며, 사용자님의 CustomUserDetails 객체일 수도 있기때문
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            log.info("로그인 성공 로그인아이디: {}, JWT 생성 및 발급 완료", userDetails.getUser().getLoginId());
            
            // 세션 방식
//            session.setAttribute("SPRING_SECURITY_CONTEXT",SecurityContextHolder.getContext());
//            log.info("로그인 성공 LoginId : {}, SessionId : {}", userDetails.getUsername(), session.getId());

            Cookie cookie = new Cookie("accessToken", jwtToken);
            cookie.setHttpOnly(true); // JavaScript 접근 불가
            cookie.setSecure(false); // HTTPS 로만
            cookie.setPath("/"); // 모든경로
            cookie.setMaxAge(60 * 60 * 24); // 1일 설정 만료일 설정
            httpServletResponse.addCookie(cookie);

//         응답 로직
            Map<String, Object> LoginResponse = new HashMap<>();
            LoginResponse.put("success", true);
            LoginResponse.put("massage", "로그인 성공");
//            response.put("token", jwtToken);
            LoginResponse.put("user", UserLoginResponse.builder()
                    .id(userDetails.getUser().getId())
                    .loginId(userDetails.getUsername())
                    .name(userDetails.getUser().getName())
                    .userRole(userDetails.getUser().getUserRole())
                    .build());
            return ResponseEntity.ok(LoginResponse);

        } catch (AuthenticationException e) {
            log.info("로그인 실패 여기는 catch LoginId : {}, Error 사유: {}", userLoginRequest.getLoginId(), e.getMessage());

            Map<String, Object> response = new HashMap<>();
            response.put("success",false);
            response.put("massage","아이디 또는 비밀번호가 일치하지 않습니다.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/logout")
    public ResponseEntity<?> logout (HttpServletResponse httpServletResponse) {

        log.info("로그아웃 요청 호출");

        Cookie cookie =  new Cookie("accessToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // HTTPS에서만 사용가능,
        cookie.setPath("/");
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "로그아웃 되었습니다.");
        log.info("로그아웃 완료 및 쿠키 삭제 완료");

        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/currentUser")
    public ResponseEntity<Map<String, Object>> currentUser () {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if( authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        System.out.println("여기는 currentUser 컨트롤러");

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("여기는 로그인된 사용자정보 불러오기 : {}", userDetails);
        log.info("여기는 로그인된 사용자정보 불러오기 : {}", userDetails.getUsername());
        log.info("여기는 로그인된 사용자정보 불러오기 : {}", userDetails.getUser().getId());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("user", UserLoginResponse.builder()
                        .id(userDetails.getUser().getId())
                        .loginId(userDetails.getUser().getLoginId())
                        .name(userDetails.getUser().getName())
                        .userRole(userDetails.getUser().getUserRole())
                        .build());
        log.info("여기는 로그인된 사용자정보 반환: {}",response);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-loginId")
    public ResponseEntity<LoginIdCheckResponse> checkLoginId (@RequestParam String loginId) {
        // @requestParam = QueryParameter 값을 추출해서 해당 인자 String loginId로 변환해서 받는다.
        // 프론트 요청에서 Params로 보냈기때문에 즉, 쿼리파라미터로 보냈기 때문에 해당 어노테이션 사용
        boolean isDuplicate = userService.checkLoginId(loginId); // service의 existsByLoginId 메서드 사용
        String message = isDuplicate ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디 입니다.";

        LoginIdCheckResponse response = new LoginIdCheckResponse(isDuplicate, message);
        // true / false만 반환하는 대신에 중복여부확인과 프론트에 전달할 메세지까지 객체형태로 담아서 전달
        return ResponseEntity.ok(response);
    }


    // @RequestParam 방식은 쿼리파라미터를 보내는 방식으로 REST API 원칙과는 다른방식
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/profile/{loginId}")
    public ResponseEntity<UserProfileResponse> getUserProfile (@PathVariable String loginId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("여기는 프로필조회 인증/인가 확인결과 :{}", authentication);
        UserProfileResponse profileResponse = userService.getUserProfile(loginId);
        return ResponseEntity.ok(profileResponse);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/profile-modify")
    public ResponseEntity<UserModifyResponse> modifyUserProfile (@RequestBody UserModifyRequest userModifyRequest) {
        UserModifyResponse response = userService.modifyUserProfile(userModifyRequest);
        if(!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
        }


    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/password-change")
    public ResponseEntity<PasswordChangeResponse> changeUserPassword (@RequestBody PasswordChangeRequest request) {
        PasswordChangeResponse response = userService.changeUserPassword(request);
        if(!response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/withdraw")
    public ResponseEntity<UserWithdrawalResponse> withdrawUser (@RequestBody UserWithdrawalRequest request) {
        UserWithdrawalResponse response = userWithdrawalService.withdrawUser(request);
        log.info("여기는 회원탈퇴 컨트롤러 : {} ", response);
        if(!response.isSuccess()){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }



}
