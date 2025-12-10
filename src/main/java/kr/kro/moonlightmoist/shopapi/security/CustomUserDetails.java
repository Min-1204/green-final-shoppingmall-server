package kr.kro.moonlightmoist.shopapi.security;

import kr.kro.moonlightmoist.shopapi.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // UserRole을 Spring Security 권한으로 변환하는 메서드
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // 유저의 비밀번호를 가져옴
    }

    @Override
    public String getUsername() {
        return user.getLoginId(); // 유저의 loginId를 가져옴
    }

    private Long getId() {
        return user.getId(); // 유저의 Long Id를 가져옴
    }

    private String getName() {
        return user.getName(); // 유저의 name을 가져옴
    }

    private String getEmail() {
        return user.getEmail(); // 유저의 email을 가져옴
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 영구 사용 가능으로 설정
        // 계정 만료 여부: 계정 자체가 사용 가능한 기간이 지났는지 확인
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isDeleted(); // 탈퇴하지 않았다면 잠그지 않음
        // 계정 잠금 여부: 비밀번호를 여러 번 틀려서 계정이 잠겼는지 확인
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 정책 없음으로 설정
        // 비밀번호 만료 여부: 비밀번호를 주기적으로 변경해야 하는 정책이 있을 때, 비밀번호가 만료되었는지 확인
    }

    @Override
    public boolean isEnabled() {
        return !user.isDeleted();
        //계정 활성화 여부: 계정이 활성화(사용 가능한) 상태인지 확인
    }
}
