package kr.kro.moonlightmoist.shopapi.usercoupon.exception;

import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class DuplicationCouponIssueException extends BusinessException {
    public DuplicationCouponIssueException() {
        super(HttpStatus.CONFLICT, "이미 발급받은 쿠폰입니다.");
    }
}
