package kr.kro.moonlightmoist.shopapi.helpcenter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class InquiryListDTO {

    private List<InquiryDTO> inquiries;
    private int totalCount;

}
