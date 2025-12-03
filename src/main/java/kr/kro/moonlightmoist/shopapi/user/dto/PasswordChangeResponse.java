package kr.kro.moonlightmoist.shopapi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordChangeResponse {

    private boolean Success;
    private String message;

}
