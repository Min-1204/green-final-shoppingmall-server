package kr.kro.moonlightmoist.shopapi.user.useradminexception;

import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(Long userId) {
        super(HttpStatus.NOT_FOUND, "User not found. userId = " + userId);
    }
}
