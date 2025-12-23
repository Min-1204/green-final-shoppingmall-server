package kr.kro.moonlightmoist.shopapi.user.service;

import jakarta.transaction.Transactional;
import kr.kro.moonlightmoist.shopapi.review.dto.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.PageResponseDTO;
import kr.kro.moonlightmoist.shopapi.user.useradminexception.UserNotFoundException;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserRole;
import kr.kro.moonlightmoist.shopapi.user.dto.UserSearchCondition;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserAdminServiceImpl implements UserAdminService{

    private final UserRepository userRepository;

    @Override
    public PageResponseDTO<User> searchUsers(UserSearchCondition condition, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize()
        );

        Page<User> page = userRepository.search(condition, pageable);

        PageResponseDTO<User> pageResponseDTO = PageResponseDTO.<User>withAll()
                .dtoList(page.getContent())
                .pageRequestDTO(pageRequestDTO)
                .totalDataCount((int) page.getTotalElements())
                .build();

        return pageResponseDTO;
    }

    @Override
    public void userRoleChange(Long userId, UserRole userRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.changeRole(userRole);
    }
}
