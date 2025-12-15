package kr.kro.moonlightmoist.shopapi.order.dto;

import kr.kro.moonlightmoist.shopapi.order.domain.OrderProductStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchCondition {
    private Long userId;
    private String orderNumber;
    private String ordererName;
    private String productName;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<OrderProductStatus> selectedOrderStatus;
    private List<String> selectedDelivery;
    private List<String> selectedPayment;
}
