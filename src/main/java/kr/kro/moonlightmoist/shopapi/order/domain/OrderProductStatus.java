package kr.kro.moonlightmoist.shopapi.order.domain;

public enum OrderProductStatus {
    //결제 대기(주문 접수)
    PENDING_PAYMENT,
    //결제 완료(결제 확인)
    PAID,
    //배송 준비중
    PREPARING,
    //배송중
    SHIPPING,
    //배송 완료
    DELIVERED,
    // 구매 확정
    CONFIRMED,
    //취소 신청
    CANCEL_REQUESTED,
    // 취소 완료
    CANCELED,
    //교환 신청
    EXCHANGE_REQUESTED,
    //교환 완료
    EXCHANGED,
    //반품 신청
    RETURN_REQUESTED,
    //반품 완료
    RETURNED

}
