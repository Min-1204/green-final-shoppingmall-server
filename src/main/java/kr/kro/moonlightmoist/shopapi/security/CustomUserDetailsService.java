package kr.kro.moonlightmoist.shopapi.security;

import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        log.info("로그인 시도 로그인아이디 : {}", loginId);

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("사용자를 찾을 수 없습니다." + loginId);
                });

        return new CustomUserDetails(user);
    }

}
