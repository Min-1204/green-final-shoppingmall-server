package kr.kro.moonlightmoist.shopapi.coupon.exception;

import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class InvalidCouponCodeException extends BusinessException {
    public InvalidCouponCodeException() {
        super(HttpStatus.NOT_FOUND, "잘못된 쿠폰 코드입니다.");
    }
}