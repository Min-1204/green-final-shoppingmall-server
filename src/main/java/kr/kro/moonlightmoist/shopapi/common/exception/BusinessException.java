package kr.kro.moonlightmoist.shopapi.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException{
    private final HttpStatus status;
    private final String errorMessage;

    public BusinessException(HttpStatus status, String errorMessage) {
        super(errorMessage);
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
