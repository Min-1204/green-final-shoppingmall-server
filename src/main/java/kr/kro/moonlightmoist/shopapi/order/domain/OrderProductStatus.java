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
    //반품 신청(배송 후)
    RETURN_REQUESTED,
    //반품 완료(환불 처리 완료)
    RETURNED

}
