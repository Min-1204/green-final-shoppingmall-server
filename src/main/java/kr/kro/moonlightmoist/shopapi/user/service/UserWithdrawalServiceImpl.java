package kr.kro.moonlightmoist.shopapi.user.service;

import jakarta.transaction.Transactional;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserWithdrawal;
import kr.kro.moonlightmoist.shopapi.user.dto.UserWithdrawalRequest;
import kr.kro.moonlightmoist.shopapi.user.dto.UserWithdrawalResponse;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import kr.kro.moonlightmoist.shopapi.user.repository.UserWithdrawalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserWithdrawalServiceImpl implements UserWithdrawalService {

    private final UserRepository userRepository;
    private final UserWithdrawalRepository userWithdrawalRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserWithdrawalResponse withdrawUser(UserWithdrawalRequest request) {
        log.info("여기는 회원탈퇴 요청 : {}", request);
        User user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));
        if(user.isDeleted()) {
            return UserWithdrawalResponse.builder()
                    .success(false)
                    .message("이미 탈퇴한 회원입니다.")
                    .build();
        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return UserWithdrawalResponse.builder()
                    .success(false)
                    .message("비밀번호가 일치하지 않습니다.")
                    .build();
        }

        user.withdraw();

        UserWithdrawal withdrawalUser = UserWithdrawal.builder()
                .user(user)
                .userWithdrawalReason(request.getUserWithdrawalReason())
                .build();

        userWithdrawalRepository.save(withdrawalUser);


        return UserWithdrawalResponse.builder()
                .success(true)
                .message("회원 탈퇴가 정상 처리 되었습니다.")
                .build();
    }
}
