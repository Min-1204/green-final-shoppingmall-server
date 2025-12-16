package kr.kro.moonlightmoist.shopapi.order.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResBySearch {
    private Long id;
    private LocalDate orderDate;
    private String orderNumber;
    @Builder.Default
    private List<OrderProductResBySearch> orderProducts = new ArrayList<>();
    private String receiverName;
    private String ordererName;
    private String paymentMethod;
    private int earnedPoints;

}
