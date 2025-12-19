package kr.kro.moonlightmoist.shopapi.common.exceptionhandler;

import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import kr.kro.moonlightmoist.shopapi.common.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error("BusinessException: {}", e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.of(e);

        return new ResponseEntity<>(errorResponse, e.getStatus());
    }

}
