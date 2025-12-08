package kr.kro.moonlightmoist.shopapi.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class OrderCouponResponseDTO {
    // 주문 쿠폰 엔티티의 id
    private Long id;
    private int discountAmount;
    private String discountType;
    private String couponCode;
    private LocalDateTime appliedAt;
    private String userCouponName;

}
