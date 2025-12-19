package kr.kro.moonlightmoist.shopapi.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class ErrorResponse {
    private boolean success;
    private int status;
    private String message;
    private LocalDateTime timestamp;

    public static ErrorResponse of(BusinessException e) {
        return ErrorResponse.builder()
                .success(false)
                .status(e.getStatus().value())
                .message(e.getErrorMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
