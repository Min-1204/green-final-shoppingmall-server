package kr.kro.moonlightmoist.shopapi.user.service;

import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.dto.*;


public interface UserService {
    User registerUser(UserSignUpRequest userSignUpRequest);
    UserLoginResponse login(UserLoginRequest userLoginRequest);
    boolean checkLoginId(String loginId);
    UserProfileResponse getUserProfile(String loginId);
    UserModifyResponse modifyUserProfile(UserModifyRequest userModifyRequest);
    PasswordChangeResponse changeUserPassword(PasswordChangeRequest passwordChangeRequest);
}
