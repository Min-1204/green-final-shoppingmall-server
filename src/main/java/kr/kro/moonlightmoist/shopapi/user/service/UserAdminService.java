package kr.kro.moonlightmoist.shopapi.user.service;

import kr.kro.moonlightmoist.shopapi.review.dto.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.PageResponseDTO;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserRole;
import kr.kro.moonlightmoist.shopapi.user.dto.UserSearchCondition;


public interface UserAdminService {
    //회원 조회
    PageResponseDTO<User> searchUsers(UserSearchCondition condition, PageRequestDTO pageRequestDTO);
    //회원 권한 변경
    void userRoleChange(Long userId, UserRole userRole);
}
