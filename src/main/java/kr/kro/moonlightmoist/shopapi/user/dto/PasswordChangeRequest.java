package kr.kro.moonlightmoist.shopapi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordChangeRequest {

    private String loginId;
    private String password;
    private String newPassword;

}
