package kr.kro.moonlightmoist.shopapi.user.service;

import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserGrade;
import kr.kro.moonlightmoist.shopapi.user.dto.UserSearchCondition;

import java.util.List;

public interface UserAdminService {
    //회원 조회
    List<User> searchUsers(UserSearchCondition condition);
//    SMS 발송
//    void sendSmsToUsers(List<Long> userIds, String message);
//    이메일 발송
//    void sendEmailToUsers(List<Long> userIds, String subject, String content);
//    회원 상태 변경
//    void updateUserStatus(Long userId, boolean deleted)
//    회원 등급 변경
//    void updateUserGrade(Long userId, UserGrade grade);
}
