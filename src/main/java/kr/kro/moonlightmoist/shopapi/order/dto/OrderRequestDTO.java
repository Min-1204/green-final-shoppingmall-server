package kr.kro.moonlightmoist.shopapi.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequestDTO { // 주문 요청 DTO
    private String paymentMethod;
    private int deliveryFee;

    //배송정보
    private String receiverName;
    private String receiverPhone;
    private String postalCode;
    private String streetAddress;
    private String detailedAddress;
    private String deliveryRequest;

    // 장바구니 또는 상품 옵션 ID + 수량
    private List<OrderProductRequestDTO> orderProducts;

    private Long couponId; // 쿠폰 아이디
    private int usedPoints; // 사용 포인트

}
