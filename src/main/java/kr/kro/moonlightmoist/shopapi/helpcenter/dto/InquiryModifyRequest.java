package kr.kro.moonlightmoist.shopapi.helpcenter.dto;

import kr.kro.moonlightmoist.shopapi.helpcenter.domain.InquiryType;
import lombok.Getter;

@Getter
public class InquiryModifyRequest {

    private String title;
    private String content;
    private InquiryType inquiryType;
    private boolean emailAgreement;
    private boolean smsAgreement;

}
