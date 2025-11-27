package kr.kro.moonlightmoist.shopapi.order.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private String orderNumber;
    private String paymentMethod;
    private LocalDate expectedDeliveryDate;
    private int totalProductAmount;
    private int deliveryFee;
    private int discountAmount;
    private int usedPoints;
    private int finalAmount;
    private String receiverName;
    private String receiverPhone;
    private String postalCode;
    private String streetAddress;
    private String detailedAddress;
    private String deliveryRequest;
    @Builder.Default
    private List<OrderProductResponseDTO> orderProducts = new ArrayList<>();

    private LocalDate orderDate;

}
