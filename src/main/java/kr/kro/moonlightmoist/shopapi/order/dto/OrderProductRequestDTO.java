package kr.kro.moonlightmoist.shopapi.order.dto;

import kr.kro.moonlightmoist.shopapi.order.domain.OrderProductStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductRequestDTO {
    private Long id;
    private Long orderId;
    private Long productOptionId;
    private int quantity;
    private int purchasedPrice;
    private OrderProductStatus orderProductStatus;
}
