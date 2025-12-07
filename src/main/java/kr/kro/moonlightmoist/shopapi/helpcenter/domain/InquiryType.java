package kr.kro.moonlightmoist.shopapi.helpcenter.domain;

import lombok.Getter;

@Getter
public enum InquiryType {
    DELIVERY("배송문의"),
    ORDER("주문문의"),
    RETURN("반품/교환"),
    MEMBER_INFO("회원정보"),
    MEMBER_POINT("포인트"),
    ETC("기타");

    private final String typeName;

    InquiryType(String typeName){
        this.typeName = typeName;
    }

    public String getTypeName () {
        return typeName;
    }
}
