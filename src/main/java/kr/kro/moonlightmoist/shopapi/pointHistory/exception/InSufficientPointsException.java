package kr.kro.moonlightmoist.shopapi.pointHistory.exception;

import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class InSufficientPointsException extends BusinessException {
    public InSufficientPointsException() {
        super(HttpStatus.BAD_REQUEST, "포인트가 부족합니다.");
    }
}
