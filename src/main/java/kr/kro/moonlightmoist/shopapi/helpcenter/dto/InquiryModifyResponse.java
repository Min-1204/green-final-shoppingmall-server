package kr.kro.moonlightmoist.shopapi.helpcenter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class InquiryModifyResponse {

    private Long id;
    private String title;
    private String inquiryContent;
    private String inquiryType;
    private String inquiryTypeName;
    private LocalDateTime updatedAt;

}
