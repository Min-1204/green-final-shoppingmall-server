package kr.kro.moonlightmoist.shopapi.helpcenter.dto;

import jakarta.validation.constraints.NotBlank;
import kr.kro.moonlightmoist.shopapi.helpcenter.domain.InquiryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InquiryCreateRequest {

    private String loginId;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "문의 유형을 선택해주세요")
    private InquiryType inquiryType;

    @NotBlank(message = "내용을 입력 해주세요")
    private String content;

    private boolean emailAgreement;

    private boolean smsAgreement;

}

