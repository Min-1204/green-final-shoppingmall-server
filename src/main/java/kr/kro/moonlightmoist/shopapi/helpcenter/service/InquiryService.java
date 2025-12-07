package kr.kro.moonlightmoist.shopapi.helpcenter.service;


import kr.kro.moonlightmoist.shopapi.helpcenter.dto.*;

public interface InquiryService {
    InquiryCreateResponse registerInquiry (InquiryCreateRequest createRequest);
    InquiryListDTO getInquiryList(String loginId);
    InquiryModifyResponse modifyInquiry(Long id, InquiryModifyRequest request, String loginId);
    void deleteInquiry(Long id, String loginId);
}
