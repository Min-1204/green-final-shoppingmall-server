package kr.kro.moonlightmoist.shopapi.helpcenter.dto;

import kr.kro.moonlightmoist.shopapi.helpcenter.domain.Inquiry;
import kr.kro.moonlightmoist.shopapi.helpcenter.domain.InquiryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class InquiryDTO {

    private Long id;
    private String title; // 제목
    private String content; // 내용
    private InquiryType inquiryType; // 문의 유형
    private String InquiryTypeName; // 프론트반환용
    private LocalDateTime createdAt; // 작성일자
    private LocalDateTime updatedAt; // 수정일자
    private boolean emailAgreement;
    private boolean smsAgreement;
    private boolean answered; // 답변완료 여부
    private String answerContent; // 답변내용
    private LocalDateTime answerCreatedAt; // 답변일자


    public static InquiryDTO toInquiryDTO (Inquiry inquiry) {
        return InquiryDTO.builder()
                .id(inquiry.getId())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .inquiryType(inquiry.getInquiryType())
                .InquiryTypeName(inquiry.getInquiryType().getTypeName())
                .createdAt(inquiry.getCreatedAt())
                .updatedAt(inquiry.getUpdatedAt())
                .emailAgreement(inquiry.isEmailAgreement())
                .smsAgreement(inquiry.isSmsAgreement())
                .answered(inquiry.isAnswered())
                .answerContent(inquiry.getAnswerContent())
                .answerCreatedAt(inquiry.getAnswerCreatedAt())
                .answered(inquiry.isAnswered())
                .build();
    }
}
